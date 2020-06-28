package master.abreaking.flowable.doc;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.List;

/**
 * BPM2.0流程部署 学习
 * learn from : https://tkjohn.github.io/flowable-userguide/#bpmn20
 * @author liwei_paas 
 * @date 2020/6/18
 */
public class FlowableDeploymentMaster {

    /**
     * 官方文档上给的例子
     * @author liwei_paas
     * @date 2020/6/18
     */
    public static void main(String args[]){
        // 创建Flowable流程引擎
        ProcessEngine processEngine = ProcessEngineBuilder.build();

        // 获取Flowable服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 部署流程定义
        repositoryService.createDeployment()
                .addClasspathResource("mytest.bpmn20.xml")
                .deploy();

        // 启动流程实例
        String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

        // 获取第一个任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());

            // 申领任务
            taskService.claim(task.getId(), "fozzie");
        }

        // 验证Fozzie获取了任务
        tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : tasks) {
            System.out.println("Task for fozzie: " + task.getName());

            // 完成任务
            taskService.complete(task.getId());
        }

        System.out.println("Number of tasks for fozzie: "
                + taskService.createTaskQuery().taskAssignee("fozzie").count());

        // 获取并申领第二个任务
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for management group: " + task.getName());
            taskService.claim(task.getId(), "kermit");
        }

        // 完成第二个任务并结束流程
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }

        // 验证流程已经结束
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
        System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
    }

    public static void mainByLiwei(String args[]){
        ProcessEngine processEngine = ProcessEngineBuilder.build();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();


        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("mytest.bpmn20.xml")
                .deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        String tempTaskId = tasks.get(0).getId();
        taskService.claim(tempTaskId,"liwei");
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("liwei").list();
        System.out.println(taskList);
        taskService.complete(tempTaskId);
        System.out.println(taskList);

        System.out.println("done");

    }
}
