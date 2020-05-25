var appId = -1;
var envId = -1;
var version = "";
getSession();

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var env = $("#env").val();

    // 验证
    if (!env) {
        $("#error").removeClass("hide");
        $("#error").html("表单不能为空或填写格式错误！");
        return;
    }
    if(env.length > 20){
        $("#error").removeClass("hide");
        $("#error").html("环境名称长度不能超过20！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/api/env",
        data: {
            "env": env
        }
    }).done(function (data) {
        $("#error").removeClass("hide");
        if (data.success === "true") {
            $("#error").html(data.result);
        } else {
            Util.input.whiteError($("#error"), data);
        }
    });
});
