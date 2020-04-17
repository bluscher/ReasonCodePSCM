/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.reasoncode;

import java.util.*;
import com.experian.eda.component.decisionagent.LookUpManager;

public class reasoncode {
    
    public static void main(String[] args) 
	{  
                String code = null;    
                String desc = null;    
                
		try 
		{
                   
                    LookUpManager lookup = LookUpManager.getInstance("Decision Reason Codes", "ClevCard");
                         
                    List myCodes = lookup.getAllReasonCodes();
                    Map myDescs = lookup.getAllReasonCodeDescs();
                    
                
                    Iterator itrCodes = myCodes.iterator();
                    while (itrCodes.hasNext()) 
                    {
                          
                            code = (String) itrCodes.next();
                            
                            
                            desc = (String) myDescs.get(code);

               
                            System.out.println("code= " + code + "   description= " +  desc);
                    }   
		}
                catch (Exception e) 
		{
			System.out.println("Exception: " + e);
		} // end catch
	}
    
    
}
