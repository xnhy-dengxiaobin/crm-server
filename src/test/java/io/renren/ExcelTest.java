package io.renren;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.busi.service.BusiCustomerFollowService;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.busi.service.MiddleTypeService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTest {
    @Autowired
    private BusiCustomerService customerService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BusiCustomerFollowService followService;

    @Autowired
    private MiddleTypeService middleTypeService;

    @Test
    public void userInfoExcel(){
        ImportParams params = new ImportParams();

        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
                new File("/Users/liqilong/projets/crm-s/crm-server/src/test/java/io/renren/qmjjr.xlsx"), Map.class, params);
        System.out.println(list);
        List<SysUserEntity> sysUserEntities = new ArrayList<>();
        for (Map<String, Object> map : list) {
            String dbMiddleName = null;
            Integer dbMiddleId = null;
            String middleName = toString(map.get("身份信息"));
            String suoshu = toString(map.get("所属公司"));
            SysUserEntity user = new SysUserEntity();
            String phone = toString(map.get("经纪人电话")).replace("\t", "");
            user.setUsername(phone);
            user.setName(toString(map.get("经纪人名称")));
            user.setMobile(phone);
            if(middleName != null){
                MiddleTypeEntity one = middleTypeService.getOne(new QueryWrapper<MiddleTypeEntity>().lambda().eq(MiddleTypeEntity::getName, middleName));
                if(one == null){
                    MiddleTypeEntity middleTypeEntity = new MiddleTypeEntity();
                    middleTypeEntity.setName(middleName);
                    middleTypeEntity.setType("1");
                    middleTypeEntity.setStatus(1);
                    middleTypeService.save(middleTypeEntity);
                    dbMiddleName = middleName;
                    dbMiddleId = middleTypeEntity.getId();
                    one = middleTypeEntity;
                }else {
                    dbMiddleName = one.getName();
                    dbMiddleId = one.getId();
                }
                if(middleName.equals("中介公司") && suoshu != null){ // 如果等于中介公司并且所属公司不为空
                    MiddleTypeEntity s = middleTypeService.getOne(new QueryWrapper<MiddleTypeEntity>().lambda().eq(MiddleTypeEntity::getName, suoshu));
                    if(s == null){
                        MiddleTypeEntity middleTypeEntity = new MiddleTypeEntity();
                        middleTypeEntity.setName(suoshu);
                        middleTypeEntity.setType("1");
                        middleTypeEntity.setStatus(1);
                        middleTypeEntity.setParentId(one.getId());
                        middleTypeService.save(middleTypeEntity);
                        dbMiddleName = middleName;
                        dbMiddleId = middleTypeEntity.getId();
                    }else{
                        dbMiddleName = s.getName();
                        dbMiddleId = s.getId();
                    }

                }
            }
            user.setMiddleTypeName(dbMiddleName);
            user.setMiddleTypeId(dbMiddleId != null?dbMiddleId+"":null);
            String status = toString(map.get("禁用状态"));
            user.setStatus(status.equals("未禁用")? 1:0);
            user.setSex(toString(map.get("经纪人性别")));
            user.setCreateTime(toDate(map.get("注册时间2")));
            user.setPassword("123");
            user.setCreateTime(new Date());
            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
            user.setSalt(salt);

            //拼音首字母
            String s = PinyinHelper.toPinyin(user.getName(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);
            user.setFirstLetter(s);
            sysUserEntities.add(user);
        }
        sysUserService.saveBatch(sysUserEntities);
    }

    @Test
    public void followInfoExcel() throws Exception{
        ImportParams params = new ImportParams();

        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
                new File("/Users/liqilong/projets/crm-s/crm-server/src/test/java/io/renren/gjjl.xlsx"), Map.class, params);
        System.out.println(list);
        List<BusiCustomerFollowEntity> dateList =  new ArrayList<>();
        for (Map<String, Object> map : list) {
            String name = toString(map.get("姓名"));
            String mobile_phone = toDoubel(map.get("电话"));
            String createName = toString(map.get("业务员"));
            String grade = toString(map.get("意向级别"));
            Date createTime = toDate(map.get("跟进时间2"));
            String mode = toString(map.get("跟进方式"));
            String content = toString(map.get("跟进内容"));
            Date nextDate = toDate(map.get("下次跟进时间2"));
            BusiCustomerFollowEntity followEntity = new BusiCustomerFollowEntity();
            followEntity.setCreateTime(createTime);
            followEntity.setCreateName(createName);
            followEntity.setContent(content);
            followEntity.setMode(mode);
            followEntity.setGrade(grade);
            followEntity.setNextDate(nextDate);
            String replace = mobile_phone.replace("\t", "");
            try {
                List<BusiCustomerEntity> list2 = customerService.list(new QueryWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getMobilePhone,replace ));
                if (list2 != null && list2.size() != 0){
                    followEntity.setCustomerId(list2.get(0).getId());
                }
            }catch (TooManyResultsException e){

            }
            dateList.add(followEntity);
        }
        followService.saveBatch(dateList);
    }

    @Test
    public void customerInfoExcel() throws Exception{
        ImportParams params = new ImportParams();

        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
                new File("/Users/liqilong/projets/crm-s/crm-server/src/test/java/io/renren/aaaaa.xls"), Map.class, params);


       // 		业务员		销售团队				无效原因	新增时间	资料完整度
        // 最近有效跟进时间	最近跟进时间	意向项目	到期日期	性别	手机	标签	备注	关注因素	意向房型	意向面积
        // 意向产品	认知途径大类	认知途径	居住区域	所属行业	家庭结构	购房用途	来访次数	证件类型
        // 证件号码	通讯地址	购房用途	关注点	竞品对比	客户来源大类	客户来源	意向级别	最近分配时间	机会id	逾期保护天数

        List<BusiCustomerEntity> listDate = new ArrayList<>();
        for (Map<String, Object> map : list) {
            BusiCustomerEntity customer = new BusiCustomerEntity();
            Object name = map.get("姓名");
            Object mobile_phone = map.get("联系方式");
            Object busi_status = map.get("客户状态");
            Object match_user = map.get("业务员");
            Object old_match_user_name = map.get("原业务员");
            Object 销售团队 = map.get("销售团队");
            Object invalid = map.get("是否无效");
            Object invalid_cause = map.get("无效原因");
            Object createTime = map.get("新增时间2");
            Object 资料完整度 = map.get("资料完整度");
            Object follow_date = map.get("最近有效跟进时间2");
            Object 最近跟进时间 = map.get("最近跟进时间2");
            Object project_id = map.get("意向项目");
            Object follow_next_date = map.get("到期日期");
            Object follow_mode = map.get("跟进方式");

            Object sex = map.get("性别");
//            Object mobile_phone = map.get("手机");
            Object 标签 = map.get("标签");
            Object remark = map.get("备注");
            Object 关注因素 = map.get("关注因素");
            Object purpose_type = map.get("意向房型");
            Object purpose_area = map.get("意向面积");
            Object purpose_product = map.get("意向产品");
            Object channel = map.get("认知途径大类");
            Object 认知途径 = map.get("认知途径");
            Object area = map.get("居住区域");
            Object profession = map.get("所属行业");
            Object family_structure = map.get("家庭结构");
            Object purpose = map.get("购房用途");
            Object come_count = map.get("来访次数");
            Object id_type = map.get("证件类型");
            Object id_number = map.get("证件号码");
            Object address = map.get("通讯地址");
            Object 购房用途 = map.get("购房用途");
            Object follow = map.get("关注点");
            Object situation = map.get("竞品对比");
            Object source_name=  map.get("客户来源大类");
            Object source_user_name = map.get("客户来源");
            Object grade = map.get("意向级别");
            Object match_user_time = map.get("最近分配时间2");
            Object 机会id = map.get("机会id");
            Object 逾期保护天数 = map.get("逾期保护天数");
            customer.setName(toString(name));
            customer.setMobilePhone(toDoubel(mobile_phone));
//            SysUserEntity one = sysUserService.getOne(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getName, match_user));
            SysUserEntity one = null;
            if(one != null){
                customer.setMatchUserId(one.getUserId()+"");
            }else{
                customer.setMatchUserId(toString(match_user));
            }
//            SysUserEntity one2 = sysUserService.getOne(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getName, old_match_user_name));
            SysUserEntity one2 = null;
            if(one2 != null){
                customer.setOldMatchUserId(one2.getUserId()+"");
                customer.setOldMatchUserName(one2.getName());
            }else{
                customer.setOldMatchUserName(toString(old_match_user_name));
            }
//            业务状态:20报备、30到访、40认筹、50认购、55交易取消和60签约
            //来访  签约 认筹 认购 询问
            Integer status = null;
            switch (toString(busi_status)) {
                case "来访":
                    status=30;
                    break;
                case "签约":
                    status=60;
                    break;
                case "认筹":
                    status=40;
                    break;
                case "认购":
                    status=50;
                    break;
                case "询问":
                    status=20;
                    break;
            }
            customer.setBusiStatus(status);
            customer.setInvalid(invalid.toString().equals("有效")?0:1);
            customer.setInvalidCause(toString(invalid_cause));

//            customer.setProjectId(project_id);
            customer.setMatchUserTime(toDate(match_user_time));
            customer.setSex(toString(sex));

            customer.setFollow(toString(follow));
            customer.setFollowMode(toString(follow_mode));
            customer.setFollowNextDate(toDate(follow_next_date));
            customer.setFollowDate(toDate(follow_date));

            customer.setCreateTime(toDate(createTime));
            customer.setAddress(toString(address));
            customer.setArea(toString(area));
            customer.setComeCount(toInt(come_count));

            customer.setFamilyStructure(toString(family_structure));
            customer.setPurpose(toString(purpose));
            customer.setPurposeArea(toString(purpose_area));
            customer.setPurposeProduct(toString(purpose_product));
            customer.setPurposeType(toString(purpose_type));

            customer.setIdType(toString(id_type));
            customer.setIdNumber(toString(id_number));
            customer.setSituation(toString(situation));

            customer.setRemark(toString(remark));
            customer.setChannel(toString(channel));
            customer.setProfession(toString(profession));
            customer.setGrade(toString(grade));
            if(source_name != null && toString(source_name).equals("来访")){
                customer.setSource("自然到访");
            }else{
                customer.setSource("渠道客户");
                customer.setSourceName(toString(source_name));
                customer.setSourceUserName(toString(source_user_name));
            }

            listDate.add(customer);
        }

        customerService.saveBatch(listDate);

    }

    public String toDoubel(Object t){
        if (t != null){
            if(t instanceof Double) {
                Double s = (Double) t;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                String s1 = nf.format(s);
                return s1;
            }else {
                return t.toString();
            }
        }
        return null;
    }
    public Date toDate(Object t) {
        try {
            if (t != null && !t.toString().equals("")){
                String s = t.toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date parse = sdf.parse(s);
                return parse;
            }
            return null;
        }catch (ParseException e){
            System.out.println(t.toString());
            return null;
//            throw new RuntimeException(e);
        }
    }

    public Integer toInt(Object t){
        if (t != null && !t.toString().equals("")){
            return Integer.parseInt(t.toString());
        }
        return null;
    }

    public String toString(Object t){
        if (t != null && !t.toString().equals("")){
            return t.toString();
        }
        return null;
    }

}
