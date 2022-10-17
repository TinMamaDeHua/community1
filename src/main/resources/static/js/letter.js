$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

// 点击弹出框的发送按钮后，调用该方法
function send_letter() {
	// 关闭弹出框
	$("#sendModal").modal("hide");
	var toName = $("#recipient-name").val();
	var content = $("#message-text").val();
	$.post(
		CONTEXT_PATH + "/letter/send",
		{
			"toName":toName,
			"content":content
		},
		function (data) {
			// 将json字符串转化为json对象
			data = $.parseJSON(data);
			if (data.code == 0) {
				$("#hintBody").text("发送成功!");
			} else {
				$("#hintBody").text(data.msg);
			}
			// 展示提示框
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 隐藏提示框后，刷新一下页面(即重新向服务器发送一次请求)
				location.reload();
			}, 2000);
		}
	)



}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}