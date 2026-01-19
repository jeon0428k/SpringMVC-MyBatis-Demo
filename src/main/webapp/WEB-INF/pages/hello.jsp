<%@ page import="com.springapp.mvc.utils.ComUtil" %>
<html>
<body>
	<h1>${message}</h1>
    test 11
    <script>

        const url = "<%=ComUtil.escapeForJs("http://localhost:8080/index")%>"

        console.log("url", url);
        alert(url)

        location.href = url;

    </script>
</body>
</html>