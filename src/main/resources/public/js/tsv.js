        $(function () {
        	$("#upload").change(function () {
                $("#filesName").val(this.value.replace(/C:\\fakepath\\/i, ''));
            });
		});

		function sendData() {
                    alert("submitting tsv file!");
                    //toggleLoading();
                    var fr = new FileReader();
                    fr.onload = function(event) {
                        var result = event.target.result;

                        $.post("/rides", result, function(result, error) {
                            result = result.replace(/\n/g, "<br>");
                            //$("#result").html(result);
                            $("#output").contents().find("body").html(result);
                            //toggleLoading();
                        });
                    };
                    var rides = $("#upload");
                    var files = rides[0]['files'];
                    var file = files[0];
                    fr.readAsText(file, 'ASCII');
        }