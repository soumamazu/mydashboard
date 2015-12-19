package SSH;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.IOptionName;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecCommand;

public class execCommands
{
    public static void main(String[] args) throws Exception
    {
        SSHExec ssh=null;
        
        try
        {
            SSHExec.setOption(IOptionName.HALT_ON_FAILURE,true);
            SSHExec.setOption(IOptionName.SSH_PORT_NUMBER,22);
            SSHExec.setOption(IOptionName.ERROR_MSG_BUFFER_TEMP_FILE_PATH,"sample.err");
            SSHExec.setOption(IOptionName.INTEVAL_TIME_BETWEEN_TASKS,100l);
            SSHExec.setOption(IOptionName.TIMEOUT,3600l);
            SSHExec.showEnvConfig();

            ConnBean cb=new ConnBean("vmohsnbty012.oracleoutsourcing.com","soummazu","D33Pm@zz");
            ssh=SSHExec.getInstance(cb);
            String pbrun="pbrun ohsdba -u ordnbt2o ";
            CustomTask task=new ExecCommand(pbrun+"'ksh' '/home/soummazu/preHC_nonProd.sh' 'DNBT2O'");
            ssh.connect();
            ssh.exec(task);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            ssh.disconnect();
        }
    }
}
