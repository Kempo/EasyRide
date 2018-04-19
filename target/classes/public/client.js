
        $(document).ready(function(){
            $("#rides").change(function () {
                $('#val').text(this.value.replace(/C:\\fakepath\\/i, ''));
                /* REMOVED
                var fr = new FileReader();
                fr.onload = function () {
                    $("#displayContents").html(this.result);
                };
                fr.readAsText(this.files[0]);
                */
            });

            $("#submitSheets").click(function() {
                toggleLoading();
                var str = $("#sheetsSubmission").serialize();
                $.post("/sheets", str, function(data, error) {
                    data = data.replace(/\n/g, "<br>");
                    $("#result").contents().find("body").html(data);
                    toggleLoading();
                });
            });
        });



        function selectFile() {
            $("#rides").trigger('click');
        }

        function sendData() {
            toggleLoading();
            var fr = new FileReader();
            fr.onload = function(event) {
                var result = event.target.result;

                $.post("/rides", result, function(result, error) {
                    result = result.replace(/\n/g, "<br>");
                    //$("#result").html(result);
                    $("#result").contents().find("body").html(result);
                    toggleLoading();
                });
            };
            var rides = $("#rides");
            var files = rides[0]['files'];
            var file = files[0];
            fr.readAsText(file, 'ASCII');
        }

      function toggleLoading() {
        var status = document.getElementById("loadingImage").style.visibility;
        if(status == "" || status == "hidden") {
            document.getElementById("result").style.background = "#f7f7f7";
            document.getElementById("loadingImage").style.visibility = "visible";

        } else if (status == "visible") {
            document.getElementById("result").style.background = "white";
            document.getElementById("loadingImage").style.visibility = "hidden";
        }
      }
