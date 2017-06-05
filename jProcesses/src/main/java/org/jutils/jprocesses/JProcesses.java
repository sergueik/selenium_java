package org.jutils.jprocesses;

import org.jutils.jprocesses.info.ProcessesFactory;
import org.jutils.jprocesses.info.ProcessesService;
import org.jutils.jprocesses.model.JProcessesResponse;
import org.jutils.jprocesses.model.ProcessInfo;
import org.jutils.jprocesses.model.WindowsPriority;

import java.util.List;

/*
* @author Javier Garcia Alonso
*/
public class JProcesses {

	private boolean fastMode = false;

	private JProcesses() {
	}

	public static JProcesses get() {
		return new JProcesses();
	}

	public JProcesses fastMode() {
		this.fastMode = true;
		return this;
	}

	public JProcesses fastMode(boolean enabled) {
		this.fastMode = enabled;
		return this;
	}

	public List<ProcessInfo> listProcesses() {
		return getService().getList(fastMode);
	}

	public List<ProcessInfo> listProcesses(String processName) {
		return getService().getList(processName, fastMode);
	}

	public static List<ProcessInfo> getProcessList() {
		return getService().getList();
	}

	public static List<ProcessInfo> getProcessList(String processName) {
		return getService().getList(processName);
	}

	public static ProcessInfo getProcess(int pid) {
		return getService().getProcess(pid);
	}

	public static JProcessesResponse killProcess(int pid) {
		return getService().killProcess(pid);
	}

	public static JProcessesResponse killProcessGracefully(int pid) {
		return getService().killProcessGracefully(pid);
	}

	public static JProcessesResponse changePriority(int pid, int newPriority) {
		return getService().changePriority(pid, newPriority);
	}

	private static ProcessesService getService() {
		return ProcessesFactory.getService();
	}
}
