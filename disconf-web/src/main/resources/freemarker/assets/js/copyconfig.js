var url = location.search;
var Request = new Object();
if (url.indexOf("?") != -1) {
    var str = url.substr(1)　//去掉?号
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
        Request[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
}
var appId = Request["appId"];
var copyAppId = -1;
var envId = Request["envId"];
var copyEnvId = -1;
var version = Request["version"];
var copyVersion = "";
getSession();

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");

    if (envId == -1) {
        $("#error").removeClass("hide");
        $("#error").html("请选择环境！");
        return;
    }

    if (appId == -1) {
        $("#error").removeClass("hide");
        $("#error").html("请选择APP！");
        return;
    }

    if (!version) {
        $("#error").removeClass("hide");
        $("#error").html("请选择版本！");
        return;
    }

    if (copyEnvId == -1) {
        $("#error").removeClass("hide");
        $("#error").html("请选择复制到环境！");
        return;
    }

    if (copyAppId == -1) {
        $("#error").removeClass("hide");
        $("#error").html("请选择复制到的APP！");
        return;
    }

    if (copyVersion == '自定义版本') {
        copyVersion = $('#selfversion_value').val();
    }

    if (!copyVersion) {
        $("#error").removeClass("hide");
        $("#error").html("请选择复制到版本！");
        return;
    }

    if (envId == copyEnvId && version == copyVersion && appId == copyAppId) {
        $("#error").removeClass("hide");
        $("#error").html("不能复制到相同环境相同APP下同一版本！");
        return;
    }

    var data = {
        "appId": appId,
        "copyAppId": copyAppId,
        "envId": envId,
        "copyEnvId": copyEnvId,
        "version": version,
        "copyVersion": copyVersion
    }
    // console.log(JSON.stringify(data))

    $.ajax({
        type: "POST",
        url: "/api/web/config/copy",
        data: data
    }).done(function (data) {
        $("#error").removeClass("hide");
        if (data.success === "true") {
            $("#error").html(data.result);
        } else {
            Util.input.whiteError($("#error"), data);
        }
    });
});

$('#selfversion_value').on("change", function () {
    copyVersion = $('#selfversion_value').val();
})
