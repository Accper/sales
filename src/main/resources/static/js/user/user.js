$(document).ready(function () {

    $("#detail_tb").bootstrapTable({
        url: "user/select",
        method: 'get',
        pagination: true, //分页
        singleSelect: false,
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: "server", //可选值为 'client' 或者 'server'
        clickToSelect: true,

        queryParams: function (params) {
            return custQueryParams(params);
        },
        responseHandler: function (res) {
            return responseBeanHandler(res);
        },
        columns: [
            {checkbox: true},
            {field: 'userName', align: 'center', title: '登陆账号'},
            {field: 'fullName', align: 'center', title: '姓名'},
            {field: 'mobile', align: 'center', title: '手机号码'},
            {field: 'email', align: 'center', title: '邮箱地址'},
            {field: 'title', align: 'center', title: '职务'},
            {
                align: 'center', title: '状态', formatter: function (value, row) {
                    if (row.status == '1') {
                        return '<font color="blue">正常</font>';
                    }
                    if (row.status == '0') {
                        return '<font color="red">锁定</font>';
                    }
                }
            },
            {
                align: 'center', title: '操作', formatter: function (value, row) {
                    var text = "";
                    text = text + '<a href="javascript:void(0);" data-id="' + row.id + '" class="modify_user">修改</a>';
                    text = text + '<a href="javascript:void(0);" data-id="' + row.id + '" class="view_user">查看</a>';
                    return text;
                }
            }

        ]
    });
    var url;
    //保存
    $("#confirm_submit").click(function () {
        // 验证
        if (checkString($("#userName").val())) {
            showToast("登陆账号不能为空...");
            return;
        }
        if (checkString($("#fullName").val())) {
            showToast("姓名不能为空...");
            return;
        }
        if (checkString($("#mobile").val())) {
            showToast("手机号码不能为空...");
            return;
        }
        if (checkPhoneFormat($("#mobile").val())) {
            showToast("手机号码格式不正确...");
            return;
        }
        if (checkString($("#email").val())) {
            showToast("邮箱地址不能为空...");
            return;
        }
        if (checkEmailFormat($("#email").val())) {
            showToast("邮箱地址格式不正确...");
            return;
        }
        if (checkString($("#password").val())) {
            showToast("登陆密码不能为空...");
            return;
        }
        if ($("#select option:selected").val() == '0') {
            showToast("请选择该用户的职务...")
            return;
        }
        // 验证邮箱，用户名，手机号码是否被注册过
        if (checkUserName($("#userName").val(), $("#id").val())) {
            return;
        }
        if (checkEmail($("#email").val(), $("#id").val())) {
            return;
        }
        if (checkPhone($("#mobile").val(), $("#id").val())) {
            return;
        }

        var user = {
            userName: $("#userName").val(),
            fullName: $("#fullName").val(),
            mobile: $("#mobile").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            roleId: $("#select").val(),
            id: $("#id").val()
        };
        //调用保存方法
        $.ajax({
            url: url,
            method: 'post',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(user),
            success: function (response) {
                if (response != undefined && response.status == 1) {
                    //保存成功后关闭modal, 刷新Table
                    $('#locationInfoModel').hide();
                    refreshTable();
                } else {
                    // 显示错误信息
                }
            }
        })
    });

    //打开新增页面
    $("#insert").on('click', function (e) {
        e.preventDefault();
        $('#model-label').text('新增用户');
        $('#locationInfoModel').show();
        // 清空数据
        $("#userName").val('');
        $("#fullName").val('');
        $("#mobile").val('');
        $("#email").val('');
        $("#password").val('');
        $("#select").val('');
        $("#id").val('');
        //动态绑定select标签中的option
        $.ajax({
            url: 'user/roles',
            method: 'get',
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;
                $("#select").empty();
                $("#select").append("<option value='0'>" + "=====请选择=====" + "</option>");
                data.forEach(function (item, index) {
                    $("#select").append("<option value='" + item.id + "'>" + item.title + "</option>");
                });
            }
        });
        // 准备参数
        url = "user/register"
    });


    // 打开修改页面
    $("#detail_tb").on('click', '.modify_user', function (e) {
        e.preventDefault();
        $('#model-label').text('修改用户');
        var id = $(this).data('id');
        $('#locationInfoModel').show();
        // 先清空下拉选择框
        $("#userName").val('');
        $("#fullName").val('');
        $("#mobile").val('');
        $("#email").val('');
        $("#password").val('');
        $("#id").val('');
        //动态绑定select标签中的option
        $.ajax({
            url: 'user/roles',
            method: 'get',
            async: false,
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;
                $("#select").empty();
                $("#select").append("<option value='0'>" + "=====请选择=====" + "</option>");
                data.forEach(function (item, index) {
                    $("#select").append("<option value='" + item.id + "'>" + item.title + "</option>");
                });
            }
        });

        $.ajax({
            url: 'user/select/' + id,
            method: 'get',
            async: false,
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;
                $("#id").val(data.id);
                $("#userName").val(data.userName);
                $("#fullName").val(data.fullName);
                $("#password").val(data.password);
                $("#mobile").val(data.mobile);
                $("#email").val(data.email);
                $("#select ").val(data.roleId);
            }
        });
        url = "user/update"
    });

    // 一键锁解
    $("#lock").click(function () {
        var selecteds = $("#detail_tb").bootstrapTable('getSelections');
        var array = [];
        $.each(selecteds, function (index, row) {
            array.push(row.id);
        });

        if (array.length != 0) {
            $.ajax({
                url: 'user/lock?ids=' + array,
                method: 'get',
                async: false,
                dataType: 'json',
                success: function (response) {
                    if (response.status != 1) {
                        return;
                    }
                    showToast("共成功锁定或解锁" + response.data + "个用户...");
                    refreshTable();
                }
            });

        } else {
            showToast("请先勾选需要锁定或解锁的用户...");
        }

    });


    // 关闭模态框
    $('#cancel, #locationInfoModel .imgP').click(function () {
        $('#locationInfoModel').hide();
    });

    //根据关键字查询
    $("#search").click(function () {
        refreshTable();
    });

    //回车即可根据keyWord查询
    $('#keyWord').bind('keypress', function (event) {//监听回车事件
        if (event.keyCode == "13") {
            refreshTable();
        }
    });

    //刷新Table
    var refreshTable = function () {
        var param = {
            keyWord: $("#keyWord").val()

        };
        reloadTable("detail_tb", param);
    };

    var reloadTable = function (tableId, jsonDataParam) {
        $("#" + tableId).bootstrapTable('refreshOptions', {
            pageNumber: 1,
            queryParams: function (params) {
                return custQueryParams(params, jsonDataParam);
            }
        });
    };

    // 显示信息
    var showToast = function (message) {
        $('.toast').text(message).show().delay(3000).fadeOut();
    };

    // 判断字符串是否为空
    var checkString = function (str) {
        if (str.replace(/(^s*)|(s*$)/g, "").length == 0) {
            return true;
        }
        return false;
    };

    // 验证邮箱
    var checkEmailFormat = function check_email(email) {
        var reg = /\w+[@]{1}\w+[.]\w+/;
        if (!reg.test(email)) {
            return true;
        }
        return false;
    };

    // 验证手机号码
    var checkPhoneFormat = function check_phone(phone) {
        var reg = /(1[3-9]\d{9}$)/;
        if (!reg.test(phone)) {
            return true;
        }
        return false;
    };
    // 校验用户名是否被注册过
    var checkUserName = function (username, id) {
        // 校验用户名是否被注册过
        var flag;
        $.ajax({
            url: 'user/check/username?userName=' + username + "&id=" + id,
            async: false,
            method: 'get',
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    showToast("该登陆账号已经被注册了, 请重新输入...");
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag;
    };

    // 校验手机号码是否被注册过
    var checkPhone = function (mobile, id) {
        // 校验用户名是否被注册过
        var flag;
        $.ajax({
            url: 'user/check/phone?phone=' + mobile + "&id=" + id,
            method: 'get',
            async: false,
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    showToast("该手机号码已经被注册了, 请重新输入...");
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag;
    };

    // 校验邮箱是否被注册过
    var checkEmail = function (email, id) {
        // 校验用户名是否被注册过
        var email = {email: email, id: id};
        var flag;
        $.ajax({
            url: 'user/check/email',
            method: 'post',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(email),
            success: function (response) {
                if (response.status != 1) {
                    showToast("该邮箱地址已经被注册了, 请重新输入...");
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag;
    };

});