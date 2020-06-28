package master.abreaking.flowable.doc;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 * the first project of flowable
 * @author liwei_paas
 * @date 2020/6/15
 */
public class ProcessEngineBuilder {

    static String JDBC_URl = "jdbc:mysql://localhost:3306/flowable";
    static String JDBC_USERNAME = "root";
    static String JDBC_PASSWORD = "mysqladmin";
    static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static ProcessEngine build(){
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl(JDBC_URl)
                .setJdbcUsername(JDBC_USERNAME)
                .setJdbcPassword(JDBC_PASSWORD)
                .setJdbcDriver(JDBC_DRIVER)
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        return processEngine;
    }


}
