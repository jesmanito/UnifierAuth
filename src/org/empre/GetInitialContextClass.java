package org.empre;

import javax.naming.Context;

import weblogic.jndi.Environment;

import weblogic.management.MBeanHome;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.UserReaderMBean;

import weblogic.security.providers.authentication.DefaultAuthenticatorMBean;

public class GetInitialContextClass
    {
      public static void main(String[] args)
      {

      MBeanHome home = null;
      try
      {

        Environment env = new Environment();
        env.setProviderUrl("t3://10.0.2.15:7101");
        env.setSecurityPrincipal("weblogic");
        env.setSecurityCredentials("weblogic1");
        Context ctx = env.getInitialContext();

        home = (MBeanHome)ctx.lookup("weblogic.management.adminhome");

        weblogic.management.security.RealmMBean rmBean = 
       home.getActiveDomain().getSecurityConfiguration().getDefaultRealm();

        AuthenticationProviderMBean[] authenticationBeans = 
        rmBean.getAuthenticationProviders();
        DefaultAuthenticatorMBean defaultAuthenticationMBean = 
        (DefaultAuthenticatorMBean)authenticationBeans[0];
        UserReaderMBean userReaderMBean = 
        (UserReaderMBean)defaultAuthenticationMBean;

        String userCurName = userReaderMBean.listUsers("*", 100);

        while (userReaderMBean.haveCurrent(userCurName) )
        {
          String user = userReaderMBean.getCurrentName(userCurName);
          System.out.println("\n User: " + user);
          userReaderMBean.advance(userCurName);
        }

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      }
    }