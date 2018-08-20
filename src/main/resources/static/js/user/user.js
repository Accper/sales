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

    //打开新增页面
    $("#insert").on('click', function (e) {
        e.preventDefault();
        $('#model-label').text('新增用户');
        $('#locationInfoModel').show();
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
                data.forEach(function (item, index) {
                    $("#select").append("<option value='" + item.id + "'>" + item.title + "</option>");
                });
            }
        });

    });

    // 打开查看页面
    $("#detail_tb").on('click', '.modify_user', function (e) {
        e.preventDefault();
        $('#model-label').text('修改用户');
        $('#locationInfoModel').show();

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

});