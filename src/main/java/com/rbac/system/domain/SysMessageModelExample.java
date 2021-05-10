package com.rbac.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysMessageModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysMessageModelExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andModelKeyIsNull() {
            addCriterion("model_key is null");
            return (Criteria) this;
        }

        public Criteria andModelKeyIsNotNull() {
            addCriterion("model_key is not null");
            return (Criteria) this;
        }

        public Criteria andModelKeyEqualTo(String value) {
            addCriterion("model_key =", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyNotEqualTo(String value) {
            addCriterion("model_key <>", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyGreaterThan(String value) {
            addCriterion("model_key >", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyGreaterThanOrEqualTo(String value) {
            addCriterion("model_key >=", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyLessThan(String value) {
            addCriterion("model_key <", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyLessThanOrEqualTo(String value) {
            addCriterion("model_key <=", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyLike(String value) {
            addCriterion("model_key like", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyNotLike(String value) {
            addCriterion("model_key not like", value, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyIn(List<String> values) {
            addCriterion("model_key in", values, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyNotIn(List<String> values) {
            addCriterion("model_key not in", values, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyBetween(String value1, String value2) {
            addCriterion("model_key between", value1, value2, "modelKey");
            return (Criteria) this;
        }

        public Criteria andModelKeyNotBetween(String value1, String value2) {
            addCriterion("model_key not between", value1, value2, "modelKey");
            return (Criteria) this;
        }

        public Criteria andTitleModelIsNull() {
            addCriterion("title_model is null");
            return (Criteria) this;
        }

        public Criteria andTitleModelIsNotNull() {
            addCriterion("title_model is not null");
            return (Criteria) this;
        }

        public Criteria andTitleModelEqualTo(String value) {
            addCriterion("title_model =", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelNotEqualTo(String value) {
            addCriterion("title_model <>", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelGreaterThan(String value) {
            addCriterion("title_model >", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelGreaterThanOrEqualTo(String value) {
            addCriterion("title_model >=", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelLessThan(String value) {
            addCriterion("title_model <", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelLessThanOrEqualTo(String value) {
            addCriterion("title_model <=", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelLike(String value) {
            addCriterion("title_model like", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelNotLike(String value) {
            addCriterion("title_model not like", value, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelIn(List<String> values) {
            addCriterion("title_model in", values, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelNotIn(List<String> values) {
            addCriterion("title_model not in", values, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelBetween(String value1, String value2) {
            addCriterion("title_model between", value1, value2, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelNotBetween(String value1, String value2) {
            addCriterion("title_model not between", value1, value2, "titleModel");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescIsNull() {
            addCriterion("title_model_desc is null");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescIsNotNull() {
            addCriterion("title_model_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescEqualTo(String value) {
            addCriterion("title_model_desc =", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescNotEqualTo(String value) {
            addCriterion("title_model_desc <>", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescGreaterThan(String value) {
            addCriterion("title_model_desc >", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescGreaterThanOrEqualTo(String value) {
            addCriterion("title_model_desc >=", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescLessThan(String value) {
            addCriterion("title_model_desc <", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescLessThanOrEqualTo(String value) {
            addCriterion("title_model_desc <=", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescLike(String value) {
            addCriterion("title_model_desc like", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescNotLike(String value) {
            addCriterion("title_model_desc not like", value, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescIn(List<String> values) {
            addCriterion("title_model_desc in", values, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescNotIn(List<String> values) {
            addCriterion("title_model_desc not in", values, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescBetween(String value1, String value2) {
            addCriterion("title_model_desc between", value1, value2, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andTitleModelDescNotBetween(String value1, String value2) {
            addCriterion("title_model_desc not between", value1, value2, "titleModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelIsNull() {
            addCriterion("content_model is null");
            return (Criteria) this;
        }

        public Criteria andContentModelIsNotNull() {
            addCriterion("content_model is not null");
            return (Criteria) this;
        }

        public Criteria andContentModelEqualTo(String value) {
            addCriterion("content_model =", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelNotEqualTo(String value) {
            addCriterion("content_model <>", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelGreaterThan(String value) {
            addCriterion("content_model >", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelGreaterThanOrEqualTo(String value) {
            addCriterion("content_model >=", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelLessThan(String value) {
            addCriterion("content_model <", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelLessThanOrEqualTo(String value) {
            addCriterion("content_model <=", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelLike(String value) {
            addCriterion("content_model like", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelNotLike(String value) {
            addCriterion("content_model not like", value, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelIn(List<String> values) {
            addCriterion("content_model in", values, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelNotIn(List<String> values) {
            addCriterion("content_model not in", values, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelBetween(String value1, String value2) {
            addCriterion("content_model between", value1, value2, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelNotBetween(String value1, String value2) {
            addCriterion("content_model not between", value1, value2, "contentModel");
            return (Criteria) this;
        }

        public Criteria andContentModelDescIsNull() {
            addCriterion("content_model_desc is null");
            return (Criteria) this;
        }

        public Criteria andContentModelDescIsNotNull() {
            addCriterion("content_model_desc is not null");
            return (Criteria) this;
        }

        public Criteria andContentModelDescEqualTo(String value) {
            addCriterion("content_model_desc =", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescNotEqualTo(String value) {
            addCriterion("content_model_desc <>", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescGreaterThan(String value) {
            addCriterion("content_model_desc >", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescGreaterThanOrEqualTo(String value) {
            addCriterion("content_model_desc >=", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescLessThan(String value) {
            addCriterion("content_model_desc <", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescLessThanOrEqualTo(String value) {
            addCriterion("content_model_desc <=", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescLike(String value) {
            addCriterion("content_model_desc like", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescNotLike(String value) {
            addCriterion("content_model_desc not like", value, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescIn(List<String> values) {
            addCriterion("content_model_desc in", values, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescNotIn(List<String> values) {
            addCriterion("content_model_desc not in", values, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescBetween(String value1, String value2) {
            addCriterion("content_model_desc between", value1, value2, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andContentModelDescNotBetween(String value1, String value2) {
            addCriterion("content_model_desc not between", value1, value2, "contentModelDesc");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageIsNull() {
            addCriterion("send_system_message is null");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageIsNotNull() {
            addCriterion("send_system_message is not null");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageEqualTo(Byte value) {
            addCriterion("send_system_message =", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageNotEqualTo(Byte value) {
            addCriterion("send_system_message <>", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageGreaterThan(Byte value) {
            addCriterion("send_system_message >", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageGreaterThanOrEqualTo(Byte value) {
            addCriterion("send_system_message >=", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageLessThan(Byte value) {
            addCriterion("send_system_message <", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageLessThanOrEqualTo(Byte value) {
            addCriterion("send_system_message <=", value, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageIn(List<Byte> values) {
            addCriterion("send_system_message in", values, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageNotIn(List<Byte> values) {
            addCriterion("send_system_message not in", values, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageBetween(Byte value1, Byte value2) {
            addCriterion("send_system_message between", value1, value2, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSystemMessageNotBetween(Byte value1, Byte value2) {
            addCriterion("send_system_message not between", value1, value2, "sendSystemMessage");
            return (Criteria) this;
        }

        public Criteria andSendSmsIsNull() {
            addCriterion("send_sms is null");
            return (Criteria) this;
        }

        public Criteria andSendSmsIsNotNull() {
            addCriterion("send_sms is not null");
            return (Criteria) this;
        }

        public Criteria andSendSmsEqualTo(Byte value) {
            addCriterion("send_sms =", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotEqualTo(Byte value) {
            addCriterion("send_sms <>", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsGreaterThan(Byte value) {
            addCriterion("send_sms >", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsGreaterThanOrEqualTo(Byte value) {
            addCriterion("send_sms >=", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsLessThan(Byte value) {
            addCriterion("send_sms <", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsLessThanOrEqualTo(Byte value) {
            addCriterion("send_sms <=", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsIn(List<Byte> values) {
            addCriterion("send_sms in", values, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotIn(List<Byte> values) {
            addCriterion("send_sms not in", values, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsBetween(Byte value1, Byte value2) {
            addCriterion("send_sms between", value1, value2, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotBetween(Byte value1, Byte value2) {
            addCriterion("send_sms not between", value1, value2, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendEmailIsNull() {
            addCriterion("send_email is null");
            return (Criteria) this;
        }

        public Criteria andSendEmailIsNotNull() {
            addCriterion("send_email is not null");
            return (Criteria) this;
        }

        public Criteria andSendEmailEqualTo(Byte value) {
            addCriterion("send_email =", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotEqualTo(Byte value) {
            addCriterion("send_email <>", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailGreaterThan(Byte value) {
            addCriterion("send_email >", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailGreaterThanOrEqualTo(Byte value) {
            addCriterion("send_email >=", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailLessThan(Byte value) {
            addCriterion("send_email <", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailLessThanOrEqualTo(Byte value) {
            addCriterion("send_email <=", value, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailIn(List<Byte> values) {
            addCriterion("send_email in", values, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotIn(List<Byte> values) {
            addCriterion("send_email not in", values, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailBetween(Byte value1, Byte value2) {
            addCriterion("send_email between", value1, value2, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andSendEmailNotBetween(Byte value1, Byte value2) {
            addCriterion("send_email not between", value1, value2, "sendEmail");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}