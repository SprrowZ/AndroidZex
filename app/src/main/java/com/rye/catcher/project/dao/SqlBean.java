package com.rye.catcher.project.dao;

import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinyunyang on 15/3/9.
 */
public class SqlBean extends Bean {
    /**
     * 构建体方法
     */
    public SqlBean() {
        super();
    }

    /**
     * 构建体方法
     * @param bean 参数信息
     */
    public SqlBean(Bean bean) {
        super(bean);
    }


    /**
     * 增加关系等过滤项
     * @param key  字段 可带操作运算符, example: DATE >, TABLE_PGC_NAME like, TABLE_PGC_NAME !=  <br>
     *                 可带关系运算符, example: OR TABLE_PGC_NAME like,  AND TABLE_PGC_NAME =   <br>
     * @param value 值
     * @return
     */
    public SqlBean putCondition(String key, String value) {
      if (hasRelation(key)) {
         if (hasOperator(key)) {
             getWhere().append(" ").append(key).append(" ").append(" ?");
         } else {
             getWhere().append(" ").append(key).append(" ").append(" = ").append(" ?");
         }
      } else {
          if (hasOperator(key)) {
              getWhere().append(" and ").append(key).append(" ").append(" ?");
          } else {
              getWhere().append(" and ").append(key).append(" ").append(" = ").append(" ?");
          }
      }
        getVars().add(value);
        return this;
    }

    /**
     * 增加与关系等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean match(String field, Object value) {
        return and(field, "match", MatchUtils.tokenizeBySmart((String) value));
    }

    /**
     * 增加与关系等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean and(String field, Object value) {
        return and(field, "=", value);
    }

    /**
     * 增加与关系过滤项
     * @param field 字段
     * @param op sql操作符：=、like、in、<、>、<=、>=、<>等等
     * @param value 值
     * @return 当前对象
     */
    public SqlBean and(String field, String op, Object value) {
        getWhere().append(" and ").append(field).append(" ").append(op).append(" ?");
        getVars().add(value);
        return this;
    }


    /**
     * 增加与的in子查询关系过滤项
     * @param field 字段
     * @param subSql 子查询sql，prepared sql，支持带变量替换
     * @param subValues 子查询值，支持多个，数量与子查询中？的变量一致
     * @return 当前对象
     */
    public SqlBean andInSub(String field, String subSql, Object... subValues) {
        return andSub(field, "in", subSql, subValues);
    }

    /**
     * 增加与关系子查询过滤项
     * @param field 字段
     * @param op sql操作符：=、like、in、<、>、<=、>=等等
     * @param subSql 子查询sql，prepared sql，支持带变量替换
     * @param subValues 子查询值，支持多个，数量与子查询中？的变量一致
     * @return 当前对象
     */
    public SqlBean andSub(String field, String op, String subSql, Object... subValues) {
        getWhere().append(" and ").append(field).append(" ").append(op).append(" (").append(subSql).append(")");
        List<Object> vars = getVars();
        for (Object sub : subValues) {
            vars.add(sub);
        }
        return this;
    }

    /**
     * 增加like过滤项，全like，支持前后%
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andLike(String field, String value) {
        getWhere().append(" and ").append(field).append(" like '%' || ? || '%' ");
        getVars().add(value);
        return this;
    }

    /**
     * @param field 要比较的时间
     *  @param startTime 开始时间
     *   @param endTime 结束时间
     * */
    public SqlBean andBetween(String field,String startTime,String endTime){
        getWhere().append(" and ").append(field).append(" Between "+startTime+" and "+endTime);
        return this;
    }
    /**
     * 增加左like过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andLikeLT(String field, String value) {
        getWhere().append(" and ").append(field).append(" like '%' || ?");
        getVars().add(value);
        return this;
    }

    /**
     * 增加右like过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andLikeRT(String field, String value) {
        getWhere().append(" and ").append(field).append(" like ? || '%'");
        getVars().add(value);
        return this;
    }

    /**
     * 增加in过滤项
     * @param field 字段
     * @param value 值，支持可变数量，如果无参数自动忽略此过滤规则
     * @return 当前对象
     */
    public SqlBean andIn(String field, String... value) {
        return andIn(field, (Object[]) value);
    }

    /**
     * 增加in过滤项
     * @param field 字段
     * @param value 值，支持可变数量，如果无参数自动忽略此过滤规则
     * @return 当前对象
     */
    public SqlBean andIn(String field, Object... value) {
        int size = value.length;
        if (size > 0) {
            StringBuilder sb = preIn(value);
            getWhere().append(" and ").append(field).append(" in (").append(sb).append(")");
        }
        return this;
    }

    /**
     * 增加in过滤项
     * @param field 字段
     * @param value 值，支持可变数量，如果无参数自动忽略此过滤规则
     * @return 当前对象
     */
    public SqlBean andIn(String field, List<String> value) {
        int size = value.size();
        if (size > 0) {
            StringBuilder sb = preIn(value);
            getWhere().append(" and ").append(field).append(" in (").append(sb).append(")");
        }
        return this;
    }

    /**
     * 增加in过滤项
     * @param field 字段
     * @param value 值，支持可变数量，如果无参数自动忽略此过滤规则
     * @return 当前对象
     */
    public SqlBean andNotIn(String field, Object... value) {
        int size = value.length;
        if (size > 0) {
            StringBuilder sb = preIn(value);
            getWhere().append(" and ").append(field).append(" not in (").append(sb).append(")");
        }
        return this;
    }
    /**
     * 增加in过滤项
     * @param field 字段
     * @param value 值，支持可变数量，如果无参数自动忽略此过滤规则
     * @return 当前对象
     */
    public SqlBean andNotIn(String field,List<String> value) {
      return andNotIn(field,value.toArray());
    }

    /**
     * 增加小于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andLT(String field, Object value) {
        return and(field, "<", value);
    }

    /**
     * 增加大于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andLTE(String field, Object value) {
        return and(field, "<=", value);
    }

    /**
     * 增加小于等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andGT(String field, Object value) {
        return and(field, ">", value);
    }

    /**
     * 增加大于等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andGTE(String field, Object value) {
        return and(field, ">=", value);
    }

    /**
     * 不等于等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean andNot(String field, Object value) {
        return and(field, "<>", value);
    }

    /**
     * 增加或关系的等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean or(String field, Object value) {
        return or(field, "=", value);
    }

    /**
     * 与为null的过滤项
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean andNull(String field) {
        getWhere().append(" and ").append(field).append(" is null");
        return this;
    }

    /**
     * 与不为null的过滤项
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean andNotNull(String field) {
        getWhere().append(" and ").append(field).append(" is not null");
        return this;
    }

    /**
     * 增加或关系的过滤项
     * @param field 字段
     * @param op sql操作符：=、like、in、<、>、<=、>=等等
     * @param value 值
     * @return 当前对象
     */
    public SqlBean or(String field, String op, Object value) {
        getWhere().append(" or ").append(field).append(" ").append(op).append(" ?");
        getVars().add(value);
        return this;
    }

    /**
     * 增加或关系过滤项
     * @param field 字段
     * @param op sql操作符：=、like、in、<、>、<=、>=等等
     * @param subSql 子查询sql，prepared sql，支持带变量替换
     * @param subValues 子查询值，支持多个，数量与子查询中？的变量一致
     * @return 当前对象
     */
    public SqlBean orSub(String field, String op, String subSql, Object... subValues) {
        getWhere().append(" or ").append(field).append(" ").append(op).append(" (").append(subSql).append(")");
        List<Object> vars = getVars();
        for (Object sub : subValues) {
            vars.add(sub);
        }
        return this;
    }

    /**
     * 增加或关系like过滤项，全like，支持前后%
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean orLike(String field, String value) {
        getWhere().append(" or ").append(field).append(" like '%' || ? || '%'");
        getVars().add(value);
        return this;
    }

    /**
     * 增加或关系左like过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean orLikeLT(String field, String value) {
        getWhere().append(" or ").append(field).append(" like '%' || ?");
        getVars().add(value);
        return this;
    }

    /**
     * 增加或关系右like过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean orLikeRT(String field, String value) {
        getWhere().append(" or ").append(field).append(" like ? || '%'");
        getVars().add(value);
        return this;
    }

    /**
     * or in 相关sql
     * @param field
     * @param value
     * @return 当前对象
     */
    public SqlBean orIn(String field, Object... value) {
        int size = value.length;
        if (size > 0) {
            StringBuilder sb = preIn(value);
            getWhere().append(" or ").append(field).append(" in (").append(sb).append(")");
        }
        return this;
    }

    /**
     * or not in 相关sql
     * @param field 字段名
     * @param value 值，支持多个值
     * @return 当前对象
     */
    public SqlBean orNotIn(String field, Object... value) {
        int size = value.length;
        if (size > 0) {
            StringBuilder sb = preIn(value);
            getWhere().append(" or ").append(field).append(" not in (").append(sb).append(")");
        }
        return this;
    }


    /**
     * 或上为null的过滤项
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean orNull(String field) {
        getWhere().append(" or ").append(field).append(" is null");
        return this;
    }

    /**
     * 或上不为null的过滤项
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean orNotNull(String field) {
        getWhere().append(" or ").append(field).append(" is not null");
        return this;
    }


    /**
     * 不等于等于过滤项
     * @param field 字段
     * @param value 值
     * @return 当前对象
     */
    public SqlBean orNot(String field, Object value) {
        return or(field, "<>", value);
    }

    /**
     * 指定查询字段，支持*、distinct等
     * @param selects 查询字段
     * @return 当前对象
     */
    public SqlBean selects(String... selects) {
        set(DaoConstant.PARAM_SELECT, Lang.arrayJoin(selects));
        return this;
    }

    /**
     * 指定查询的表名，支持多个以及别名
     * @param tables 表名，支持多个以及别名
     * @return 当前对象
     */
    public SqlBean tables(String tables) {
        set(DaoConstant.PARAM_TABLE, tables);
        return this;
    }

    /**
     * 指定分组字段
     * @param groups 分组字段
     * @return 当前对象
     */
    public SqlBean groups(String groups) {
        set(DaoConstant.PARAM_GROUP, groups);
        return this;
    }

    /**
     * 指定排序SQL
     * @param orders 排序SQL
     * @return 当前对象
     */
    public SqlBean orders(String orders) {
        set(DaoConstant.PARAM_ORDER, orders);
        return this;
    }

    /**
     * 增加正序排序字段
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean asc(String field) {
        String order;
        if (contains(DaoConstant.PARAM_ORDER)) {
            order = getStr(DaoConstant.PARAM_ORDER) + "," + field;
        } else {
            order = field;
        }
        set(DaoConstant.PARAM_ORDER, order);
        return this;
    }

    /**
     * 增加倒叙排序字段
     * @param field 字段
     * @return 当前对象
     */
    public SqlBean desc(String field) {
        String order;
        if (contains(DaoConstant.PARAM_ORDER)) {
            order = getStr(DaoConstant.PARAM_ORDER) + "," + field + " desc";
        } else {
            order = field + " desc";
        }
        set(DaoConstant.PARAM_ORDER, order);
        return this;
    }

    /**
     * 设置限制数量
     * @param count 限定数量
     * @return 当前对象
     */
    public SqlBean limit(int count) {
        set(DaoConstant.PAGE_SHOWNUM, count);
        return this;
    }

    /**
     * 设置从第几页获取
     * @param page 页数
     * @return 当前对象
     */
    public SqlBean page(int page) {
        set(DaoConstant.PAGE_NOWPAGE, page);
        return this;
    }

    /**
     *
     * @return SQLite查询Sql语句中的limit限制项。
     */
    public String getLimitClause() {
        if(this.isNotEmpty(DaoConstant.PAGE_NOWPAGE)) {
            int page = this.getInt(DaoConstant.PAGE_NOWPAGE);
            int limit = this.get(DaoConstant.PAGE_SHOWNUM, 30);
            return (page * limit) + "," + limit;
        } else if (this.isNotEmpty(DaoConstant.PAGE_SHOWNUM)) {
            int limit = this.get(DaoConstant.PAGE_SHOWNUM, 30);
            return "" + limit;
        }

        return null;
    }

    /**
     * 获取拼装好的where设定条件
     * @param where 设定条件
     * @param values 对应prepared sql的变量值
     * @return 当前对象
     */
    public SqlBean appendWhere(String where, Object... values) {
        getWhere().append(where);
        if (values.length > 0) {
            getVars().addAll(Lang.asList(values));
        }
        return this;
    }

    /**
     * 获取拼装好的where设定条件
     * @return where设定条件
     */
    public StringBuilder getWhere() {
        StringBuilder sb;
        if (this.contains(DaoConstant.PARAM_WHERE)) {
            try {
                sb = (StringBuilder) this.get(DaoConstant.PARAM_WHERE);
            } catch (Exception e) {
                return new StringBuilder(this.getStr(DaoConstant.PARAM_WHERE));
            }
        } else {
            sb = new StringBuilder();
            set(DaoConstant.PARAM_WHERE, sb);
        }
        return sb;
    }

    /**
     * 获取变量值列表
     * @return 变量值
     */
    public List<Object> getVars() {
        List<Object> vars;
        if (this.contains(DaoConstant.PARAM_PRE_VALUES)) {
            vars = this.getList(DaoConstant.PARAM_PRE_VALUES);
        } else {
            vars = new ArrayList<Object>();
            this.set(DaoConstant.PARAM_PRE_VALUES, vars);
        }
        return vars;
    }

    /**
     *
     * @return 获取变量字符串数组
     */
    public String[] getVarStrings() {
        List<Object> vars = this.getVars();
        String[] result = new String[vars.size()];

        int i = 0;
        for(Object obj: vars) {
            if(obj!=null){
                result[i] = obj.toString();
            }
            i++;
        }

        return result;
    }

    /**
     * 清除所有已有的sql设定项和预设值
     * @return 当前对象
     */
    public SqlBean clears() {
        super.clear();
        return this;
    }

    /**
     * 内部方法，预处理in相关sql
     * @param value in值列表，支持多个值
     * @return in prepared sql
     */
    private StringBuilder preIn(Object... value) {
        StringBuilder sb = new StringBuilder();
        List<Object> vars = getVars();
        int size = value.length;
        for (int i = 0; i < size; i++) {
            sb.append("?,");
            vars.add(value[i]);
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }

    /**
     * 内部方法，预处理in相关sql
     * @param value in值列表，支持多个值
     * @return in prepared sql
     */
    private StringBuilder preIn(List<String> value) {
        StringBuilder sb = new StringBuilder();
        List<Object> vars = getVars();
        int size = value.size();
        for (int i = 0; i < size; i++) {
            sb.append("?,");
            vars.add(value.get(i));
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }


    private  boolean hasRelation(String key) {
        return key.trim().toLowerCase().startsWith("or") || key.trim().toLowerCase().startsWith("and");
    }

    private  boolean hasOperator(String str) {
        String regEx ="(\\s|<|>|!|=|is null|is not null|like)";
//		 忽略大小写的写法
        Pattern pattern = Pattern.compile(regEx,  Pattern.CASE_INSENSITIVE);
//	     Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str.trim());
        return matcher.find();
    }
}
