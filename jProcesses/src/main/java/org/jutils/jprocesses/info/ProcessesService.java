package org.jutils.jprocesses.info;

import org.jutils.jprocesses.model.JProcessesResponse;
import org.jutils.jprocesses.model.ProcessInfo;

import java.util.List;

/**
 * @author Javier Garcia Alonso
 */
public interface ProcessesService {
	List<ProcessInfo> getList();

	List<ProcessInfo> getList(boolean fastMode);

	List<ProcessInfo> getList(String name);

	List<ProcessInfo> getList(String name, boolean fastMode);

	ProcessInfo getProcess(int pid);

	ProcessInfo getProcess(int pid, boolean fastMode);

	JProcessesResponse killProcess(int pid);

	JProcessesResponse killProcessGracefully(int pid);

	JProcessesResponse changePriority(int pid, int priority);
}
