// 在页面加载时，会给我要发布的按钮绑定一个点击事件
$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
    // 获取标题和内容
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();
    // 发生异步请求(POST)
    $.post(
        CONTEXT_PATH + "/discuss/add",
        {
            "title":title,
            "content":content
        },
        function(data){
            // 将json字符串转化为json对象
            data = $.parseJSON(data);
            // 在提示框中显示返回信息
            $("#hintBody").text(data.msg);
            // 显示提示框
            $("#hintModal").modal("show");
            // 2秒后，自动隐藏提示框
            setTimeout(function(){
                $("#hintModal").modal("hide");
                // 若data=0，说明发帖成功，就刷新页面
                if(data.code == 0) {
                    window.location.reload();
                }
            }, 2000);
        }
    )

}
