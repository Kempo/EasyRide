        $(function () {
        	$("#submitSheets").click(function() {
                            alert("submitting sheets!");
                            //toggleLoading();
                            var str = $("#SheetsForm").serialize();
                            $.post("/sheets", str, function(data, error) {
                                data = data.replace(/\n/g, "<br>");
                                $("#output").contents().find("body").html(data);
                                //toggleLoading();
                            });
             });
		});