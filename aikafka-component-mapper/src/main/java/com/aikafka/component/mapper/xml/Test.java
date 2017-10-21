package com.aikafka.component.mapper.xml;

import com.aikafka.component.mapper.parse.JacksonUtil;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.mapper.xml
 * 类名称: Test
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
public class Test {
    public static void main(String[] args) {
        String xml="<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<Msg> \n" +
                "  <Header> \n" +
                "    <ActionCode>0</ActionCode>  \n" +
                "    <ActivityCode>220001</ActivityCode>  \n" +
                "    <RcvSys>99999</RcvSys>  \n" +
                "    <ReqDateTime>20170902101700105</ReqDateTime>  \n" +
                "    <ReqSys>2001</ReqSys>  \n" +
                "    <ReqTransID>0000021992</ReqTransID>  \n" +
                "    <Sign>d38115964b3248d65672d1327d72c7fd</Sign>  \n" +
                "    <TestFlag>0</TestFlag>  \n" +
                "    <Version>0001</Version> \n" +
                "  </Header>  \n" +
                "  <Body> \n" +
                "    <OperCode><![CDATA[2]]></OperCode>  \n" +
                "    <CompanyName><![CDATA[瑞士咨询公司40da]]></CompanyName>  \n" +
                "    <CompanyNo><![CDATA[2222222222270]]></CompanyNo>  \n" +
                "    <OrgCode><![CDATA[34334349]]></OrgCode>  \n" +
                "    <ListSitution><![CDATA[1]]></ListSitution>  \n" +
                "    <CompanyNature><![CDATA[2]]></CompanyNature>  \n" +
                "    <RegCapitalCurrency><![CDATA[1]]></RegCapitalCurrency>  \n" +
                "    <RegCapital><![CDATA[5000]]></RegCapital>  \n" +
                "    <LawMan><![CDATA[瑞士2]]></LawMan>  \n" +
                "    <LawManLicenseType><![CDATA[1]]></LawManLicenseType>  \n" +
                "    <LawManLicenseId><![CDATA[1234567890]]></LawManLicenseId>  \n" +
                "    <SubFlag><![CDATA[0]]></SubFlag>  \n" +
                "    <ChildFlag><![CDATA[0]]></ChildFlag>  \n" +
                "    <OrgCodeAttachInfo> \n" +
                "      <FileName><![CDATA[QQ图片20170731162717.jpg]]></FileName>  \n" +
                "      <FileSaveName><![CDATA[attach_20170902100500111001]]></FileSaveName>  \n" +
                "      <FileSize><![CDATA[260019]]></FileSize>  \n" +
                "      <FileType><![CDATA[application/octet-stream]]></FileType> \n" +
                "    </OrgCodeAttachInfo>  \n" +
                "    <CompanyOperating> \n" +
                "      <LicenseNo><![CDATA[234324422222245454]]></LicenseNo>  \n" +
                "      <LicenseProvinceId><![CDATA[13]]></LicenseProvinceId>  \n" +
                "      <StartDate><![CDATA[20171006]]></StartDate>  \n" +
                "      <EndDate><![CDATA[20250919]]></EndDate>  \n" +
                "      <AttachInfo> \n" +
                "        <FileName><![CDATA[QQ图片20170731162717.jpg]]></FileName>  \n" +
                "        <FileSaveName><![CDATA[attach_20170902100500111003]]></FileSaveName>  \n" +
                "        <FileSize><![CDATA[260019]]></FileSize>  \n" +
                "        <FileType><![CDATA[application/octet-stream]]></FileType> \n" +
                "      </AttachInfo> \n" +
                "    </CompanyOperating>  \n" +
                "    <IsIntegratedCertificate><![CDATA[1]]></IsIntegratedCertificate>  \n" +
                "    <CreditCode><![CDATA[234324422222245454]]></CreditCode>  \n" +
                "    <CreditCodeAttachInfo> \n" +
                "      <FileName><![CDATA[QQ图片20170731162717.jpg]]></FileName>  \n" +
                "      <FileSaveName><![CDATA[attach_20170902100500111002]]></FileSaveName>  \n" +
                "      <FileSize><![CDATA[260019]]></FileSize>  \n" +
                "      <FileType><![CDATA[application/octet-stream]]></FileType> \n" +
                "    </CreditCodeAttachInfo> \n" +
                "  </Body> \n" +
                "</Msg>";

    }
}
