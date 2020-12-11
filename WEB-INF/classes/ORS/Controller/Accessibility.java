package ORS.Controller;

public class Accessibility implements AccessPage
{
  private StringBuffer AccessCode = null;
  
  public Accessibility()
  {
  	super();
  	AccessCode = new StringBuffer("0000000000000000000000000000000");
  }
  
  public Accessibility(long code)
  {
  	super();
  	AccessCode = new StringBuffer(Long.toBinaryString(code));
  	
  	if(AccessCode.length() < 31)
  	{
  	  int val = 31 - AccessCode.length();
  		
      for(int i=0;i<val;i++)
       AccessCode.append('0');
  	}
  }
  
  public int getAccessCode()
  {
  	return Integer.valueOf(AccessCode.toString(),2);  // Max value = 2147483647
  }

  public void setAccessCode(int code)
  {
  	AccessCode = new StringBuffer(Integer.toBinaryString(code));

  	if(AccessCode.length() < 31)
  	{
  	  int val = 31 - AccessCode.length();
  		
      for(int i=0;i<val;i++)
       AccessCode.append('0');
  	}
  }

  public int getAccessValue(int page)
  {
  	page--;
  	
  	if(AccessCode != null && page < AccessPage.SIZE && page > -1)
  	  return AccessCode.charAt(page);
  	else
  	  return -1;
  }
  
  public int getAccessValue(String event)
  {
  	int page = getCode(event);
  	
  	if(page == -1)
  	  return -1;
  	else if(page == 0)
  	  return 1;
  	else
  	  return getAccessValue(page);
  }

  public boolean isAccessible(String event)
  {
  	int page = getCode(event);
  	
    if(page == 0)
  	  return true;
  	else
  	{
      page--;

  	  if(AccessCode != null && page < AccessPage.SIZE && page > -1 && AccessCode.charAt(page) == '1')
  	    return true;
  	  else
  	    return false;
  	}
  }

  public void setAccessValue(int page,boolean accessible)
  {
  	page--;

  	if(AccessCode != null && page < AccessPage.SIZE && page > -1)
  	{
      if(accessible)
      {
        AccessCode.setCharAt(page,'1');
      }
  	  else
  	  {
        AccessCode.setCharAt(page,'0');
  	  }
  	}
  }

  public void setAccessValue(String event,boolean accessible)
  {
  	int page = getCode(event);
  	
    page--;

    if(AccessCode != null && page < AccessPage.SIZE && page > -1)
    {
      if(accessible)
        AccessCode.setCharAt(page,'1');
  	  else
        AccessCode.setCharAt(page,'0');
  	}
  }

  private int getCode(String event)
  {
  	int value = 0;
  	value = Math.abs(event.hashCode());
  	
    switch(value)
    {
      case 1374524129:   if("FIRSTPAGE"  .equals(event)) value = 0;  break;
      case 946785347 :   if("ADMNACC_MN" .equals(event)) value = 0;  break;
      case 714434732 :   if("ADMNACC_CRE".equals(event)) value = 1;  break;
      case 714433229 :   if("ADMNACC_EDT".equals(event)) value = 2;  break;
      case 714434167 :   if("ADMNACC_DEL".equals(event)) value = 3;  break;
      case 587452015 :   if("CREADMN_REG".equals(event)) value = 1;  break;
      case 1404422926:   if("CREADMN_CL" .equals(event)) value = 1;  break;
      case 1597274802:   if("EDTADMN_REG".equals(event)) value = 2;  break;
      case 918305871 :   if("EDTADMN_CL" .equals(event)) value = 2;  break;
      case 1458314586:   if("POSTMGT_MN" .equals(event)) value = 0;  break;
      case 2036878673:   if("POSTMGT_CRE".equals(event)) value = 5;  break;
      case 2036880176:   if("POSTMGT_EDT".equals(event)) value = 6;  break;
      case 2036879238:   if("POSTMGT_DEL".equals(event)) value = 7;  break;
      case 1458314930:   if("POSTMGT_BK" .equals(event)) value = 0;  break;
      case 2142250139:   if("CREPOST_OK" .equals(event)) value = 5;  break;
      case 2142250510:   if("CREPOST_CL" .equals(event)) value = 5;  break;
      case 1666600102:   if("EDTPOST_OK" .equals(event)) value = 6;  break;
      case 1666599731:   if("EDTPOST_CL" .equals(event)) value = 6;  break;
      case 1349693160:   if("TESTMGT_MN" .equals(event)) value = 0;  break;
      case 1109175583:   if("TESTMGT_CRE".equals(event)) value = 8;  break;
      case 1109177086:   if("TESTMGT_EDT".equals(event)) value = 9;  break;
      case 1109176148:   if("TESTMGT_DEL".equals(event)) value = 10; break;
      case 1349693504:   if("TESTMGT_BK" .equals(event)) value = 0;  break;
      case 1121473075:   if("CRETEST_OK" .equals(event)) value = 8;  break;
      case 1121472704:   if("CRETEST_CL" .equals(event)) value = 8;  break;
      case 635356020 :   if("EDTTEST_OK" .equals(event)) value = 9;  break;
      case 635355649 :   if("EDTTEST_CL" .equals(event)) value = 9;  break;
      case 1251412651:   if("TESTPUB_MN" .equals(event)) value = 11; break;
      case 1251412836:   if("TESTPUB_GO" .equals(event)) value = 11; break;
      case 139083351 :   if("TESTPUB_PUB".equals(event)) value = 11; break;
      case 1251412963:   if("TESTPUB_CL" .equals(event)) value = 11; break;
      case 868302424 :   if("QUESMGT_MN" .equals(event)) value = 0;  break;
      case 1147561951:   if("QUESMGT_CRE".equals(event)) value = 12; break;
      case 1147577330:   if("QUESMGT_SRH".equals(event)) value = 12; break;
      case 868302239 :   if("QUESMGT_GO" .equals(event)) value = 12; break;
      case 1147563454:   if("QUESMGT_EDT".equals(event)) value = 13; break;
      case 1147562516:   if("QUESMGT_DEL".equals(event)) value = 14; break;
      case 868302080 :   if("QUESMGT_BK" .equals(event)) value = 0;  break;
      case 1391662505:   if("SELQUES_MC" .equals(event)) value = 12; break;
      case 1391662503:   if("SELQUES_MA" .equals(event)) value = 12; break;
      case 1391662725:   if("SELQUES_TF" .equals(event)) value = 12; break;
      case 1391662172:   if("SELQUES_BK" .equals(event)) value = 12; break;
      case 1174873115:   if("CREQSMC_CRE".equals(event)) value = 12; break;
      case 1146277798:   if("CREQSMC_CL" .equals(event)) value = 12; break;
      case 1176720157:   if("CREQSMA_CRE".equals(event)) value = 12; break;
      case 1146337380:   if("CREQSMA_CL" .equals(event)) value = 12; break;
      case 971698495 :   if("CREQSTF_CRE".equals(event)) value = 12; break;
      case 1139723778:   if("CREQSTF_CL" .equals(event)) value = 12; break;
      case 935367364 :   if("EDTQSMC_CRE".equals(event)) value = 13; break;
      case 1632394853:   if("EDTQSMC_CL" .equals(event)) value = 13; break;
      case 933520322 :   if("EDTQSMA_CRE".equals(event)) value = 13; break;
      case 1632454435:   if("EDTQSMA_CL" .equals(event)) value = 13; break;
      case 1138541984:   if("EDTQSTF_CRE".equals(event)) value = 13; break;
      case 1625840833:   if("EDTQSTF_CL" .equals(event)) value = 13; break;
      case 988284044 :   if("SRHQUES_GO" .equals(event)) value = 12; break;
      case 572032113 :   if("SRHQUES_EDT".equals(event)) value = 13; break;
      case 572031175 :   if("SRHQUES_DEL".equals(event)) value = 14; break;
      case 988283917 :   if("SRHQUES_CL" .equals(event)) value = 12; break;
      case 286666044 :   if("CATMGT_MN"  .equals(event)) value = 0;  break;
      case 296703355 :   if("CATMGT_CRE" .equals(event)) value = 15; break;
      case 296704858 :   if("CATMGT_EDT" .equals(event)) value = 16; break;
      case 296703920 :   if("CATMGT_DEL" .equals(event)) value = 17; break;
      case 286665700 :   if("CATMGT_BK"  .equals(event)) value = 0;  break;
      case 1194479817:   if("CRECAT_CRE" .equals(event)) value = 15; break;
      case 869815608 :   if("CRECAT_CL"  .equals(event)) value = 15; break;
      case 1680596872:   if("EDTCAT_CRE" .equals(event)) value = 16; break;
      case 1608355175:   if("EDTCAT_CL"  .equals(event)) value = 16; break;
      case 1606723530:   if("VWRSLT_MN"  .equals(event)) value = 18; break;
      case 1606723715:   if("VWRSLT_GO"  .equals(event)) value = 18; break;
      case 1731169278:   if("VWRSLT_DET" .equals(event)) value = 18; break;
      case 1731183685:   if("VWRSLT_SEL" .equals(event)) value = 18; break;
      case 1120504619:   if("VWSELD_MN"  .equals(event)) value = 19; break;
      case 375896902 :   if("VWSELD_USL" .equals(event)) value = 19; break;
      case 375913673 :   if("VWSELD_DEL" .equals(event)) value = 19; break;
      case 375905148 :   if("VWSELD_MAL" .equals(event)) value = 19; break;
      case 375900228 :   if("VWSELD_REC" .equals(event)) value = 20; break;
      case 1510241945:   if("VWEMP_MN"   .equals(event)) value = 20; break;
      case 427148813 :   if("VWEMP_DEL"  .equals(event)) value = 20; break;
      case 1399115354:   if("ADMLOG_MN"  .equals(event)) value = 21; break;
      case 1621403408:   if("USRLOG_MN"  .equals(event)) value = 0;  break;
      case 269122485 :   if("ADLOGIN_OK" .equals(event)) value = 0;  break;
      case 69210189  :   if("ADLOGOUT_MN".equals(event)) value = 0;  break;
      default        :                                   value = 0;  break;
    } 
    return value;
  } 

  public boolean equals(Object obj)
  {
    return AccessCode.toString().equals(obj);
  }

  public String toString()
  {
    return AccessCode.toString();
  }
}