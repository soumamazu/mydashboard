package SSH;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;

public class SSHUploader
{
    Session session=null;

    public void connect() throws JSchException
    {
        JSch jsch=new JSch();
        session=jsch.getSession("soummazu","vmohsnbty012.oracleoutsourcing.com",22);
        session.setPassword("D33Pm@zz");
        java.util.Properties config=new java.util.Properties();
        config.put("StrictHostKeyChecking","no");
        session.setConfig(config);
        session.connect();
    }

    public void executeCommand(String script) throws JSchException, IOException, InterruptedException
    {
        System.out.println("Execute");
        ChannelExec channel=(ChannelExec)session.openChannel("exec");
        ((ChannelExec)channel).setCommand( script);
        
        InputStream in=channel.getInputStream();
        ((ChannelExec)channel).setErrStream(System.err);
        
        channel.connect();
        
        byte[] tmp=new byte[1024];
        
        while(true)
        {
            while(in.available()>0)
            {
                int i=in.read(tmp,0,1024);
                
                if(i<0)
                    break;
                
                System.out.print(new String(tmp,0,i));
            }
        
            if(channel.isClosed())
            {
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            
            Thread.sleep(1000);
        }
    
        channel.disconnect();
        System.out.println("Sudo disconnect");
    }

    public void disconnect()
    {
        session.disconnect();
    }


    public static void main(String... args) throws JSchException, IOException, InterruptedException
    {
        SSHUploader up=new SSHUploader();
        up.connect();
        up.executeCommand("pbrun ohsdba -u ordnbt2o 'ksh' '/home/soummazu/preHC_nonProd.sh' 'DNBT2O'");
        up.disconnect();
    }
}