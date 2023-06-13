package com.matrix.cola.system.monitor.service.impl;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.system.RuntimeInfo;
import cn.hutool.system.SystemUtil;
import com.matrix.cola.common.utils.ColaUtil;
import com.matrix.cola.system.monitor.entity.*;
import com.matrix.cola.system.monitor.service.SystemMonitorService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 系统监控实现类
 *
 * @author : cui_feng
 * @since : 2022-06-11 00:19
 */
@Service
public class SystemMonitorServiceImpl implements SystemMonitorService {

    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public MonitorInfo getSystemMonitor(){
        MonitorInfo monitorInfo = new MonitorInfo();
        try {
            oshi.SystemInfo si = new oshi.SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            HardwareAbstractionLayer hal = si.getHardware();

            monitorInfo.setName("Server");
            monitorInfo.setSys(getSystemInfo(os));
            monitorInfo.setCpu(getCpuInfo(hal.getProcessor()));
            monitorInfo.setDisk(getDiskInfo(os));
            monitorInfo.setJvm(getJvmInfo());
            monitorInfo.setMemory(getMemoryInfo(hal.getMemory()));
            monitorInfo.setSwap(getSwapInfo(hal.getMemory()));
            monitorInfo.setTime(DateUtil.format(new Date(), "HH:mm:ss"));

        } catch (Exception ignore) {}
        return monitorInfo;
    }

    /**
     * 取Jvm信息
     * @return /
     */
    private JvmInfo getJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();

        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();

        long totalMemory = runtimeInfo.getTotalMemory();
        long usableMemory = runtimeInfo.getUsableMemory();
        long freeMemory = runtimeInfo.getFreeMemory();

        jvmInfo.setJavaVersion(SystemUtil.getJavaInfo().getVersion());
        jvmInfo.setJvmVersion(SystemUtil.getJvmInfo().getVersion());

        jvmInfo.setFreeMemory(ColaUtil.getSize(freeMemory));
        jvmInfo.setMaxMemory(ColaUtil.getSize(runtimeInfo.getMaxMemory()));
        jvmInfo.setTotalMemory(ColaUtil.getSize(totalMemory));
        jvmInfo.setUsableMemory(ColaUtil.getSize(usableMemory));
        jvmInfo.setTotalTread(SystemUtil.getTotalThreadCount());

        if (totalMemory == 0) {
            jvmInfo.setFreeRate("0");
        } else {
            jvmInfo.setFreeRate(df.format(freeMemory/ (double)totalMemory * 100));
        }

        if (usableMemory == 0) {
            jvmInfo.setUsedRate("0");
        } else {
            jvmInfo.setUsedRate(df.format(totalMemory/(double)usableMemory * 100));
        }
        return jvmInfo;
    }

    /**
     * 获取磁盘信息
     * @return /
     */
    private DiskInfo getDiskInfo(OperatingSystem os) {
        DiskInfo diskInfo = new DiskInfo();

        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        String osName = System.getProperty("os.name");
        long available = 0, total = 0;
        for (OSFileStore fs : fsArray){
            // windows 需要将所有磁盘分区累加，linux 和 mac 直接累加会出现磁盘重复的问题，待修复
            if(osName.toLowerCase().startsWith("win")) {
                available += fs.getUsableSpace();
                total += fs.getTotalSpace();
            } else {
                available = fs.getUsableSpace();
                total = fs.getTotalSpace();
                break;
            }
        }

        long used = total - available;

        diskInfo.setTotal(total > 0 ? ColaUtil.getSize(total) : "?");
        diskInfo.setAvailable(ColaUtil.getSize(available));
        diskInfo.setUsed(ColaUtil.getSize(used));

        if(total != 0){
            diskInfo.setUsageRate(df.format(used/(double)total * 100));
        } else {
            diskInfo.setUsageRate("0");
        }
        return diskInfo;
    }

    /**
     * 获取交换区信息
     * @param memory /
     * @return /
     */
    private SwapInfo getSwapInfo(GlobalMemory memory) {
        SwapInfo swapInfo = new SwapInfo();
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        long total = virtualMemory.getSwapTotal();
        long used = virtualMemory.getSwapUsed();

        swapInfo.setTotal(ColaUtil.getSize(total));
        swapInfo.setUsed(ColaUtil.getSize(used));
        swapInfo.setAvailable(ColaUtil.getSize(total - used));

        if(used == 0){
            swapInfo.setUsageRate("0");
        } else {
            swapInfo.setUsageRate(df.format(used/(double)total * 100));
        }
        return swapInfo;
    }

    /**
     * 获取内存信息
     * @param memory /
     * @return /
     */
    private MemoryInfo getMemoryInfo(GlobalMemory memory) {
        MemoryInfo memoryInfo = new MemoryInfo();

        memoryInfo.setTotal(ColaUtil.getSize(memory.getTotal()));
        memoryInfo.setAvailable(ColaUtil.getSize((memory.getAvailable())));
        memoryInfo.setUsed(ColaUtil.getSize((memory.getTotal() - memory.getAvailable())));
        memoryInfo.setUsageRate(df.format((memory.getTotal() - memory.getAvailable())/(double)memory.getTotal() * 100));

        return memoryInfo;
    }

    /**
     * 获取Cpu相关信息
     * @param processor /
     * @return /
     */
    private CpuInfo getCpuInfo(CentralProcessor processor) {
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setName(processor.getProcessorIdentifier().getName());
        cpuInfo.setPackages(processor.getPhysicalPackageCount() + "个物理CPU");
        cpuInfo.setCore(processor.getPhysicalProcessorCount() + "个物理核心");
        cpuInfo.setCoreNumber(processor.getPhysicalProcessorCount());
        cpuInfo.setLogic(processor.getLogicalProcessorCount() + "个逻辑CPU");

        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 等待1秒...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        cpuInfo.setUsed(df.format(100d * user / totalCpu + 100d * sys / totalCpu));
        cpuInfo.setIdle(df.format(100d * idle / totalCpu));

        return cpuInfo;
    }

    /**
     * 获取系统相关信息,系统、运行天数、系统IP
     * @param os /
     * @return /
     */
    private SystemInfo getSystemInfo(OperatingSystem os){
        SystemInfo systemInfo = new SystemInfo();

        // jvm 运行时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        String formatBetween = DateUtil.formatBetween(date, new Date(), BetweenFormatter.Level.HOUR);

        systemInfo.setOs(os.toString());
        systemInfo.setDay(formatBetween);
        systemInfo.setIp(SystemUtil.getHostInfo().getAddress());
        return systemInfo;
    }


}
