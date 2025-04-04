<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
 <title>Activiti Example</title>
 <style>
        @charset "ISO-8859-1";

body {
  background: #2d343d;
  font-family: Verdana;
}

.login {
  margin: 20px auto;
  width: 300px;
  padding: 30px 25px;
  background: white;
  border: 1px solid #c4c4c4;
}

h1.login-title {
  margin: -28px -25px 25px;
  padding: 15px 25px;
  line-height: 30px;
  font-size: 25px;
  font-weight: 300;
  color: #ADADAD;
  text-align:center;
  background: #f7f7f7;
 
}

.login-input {
  width: 285px;
  height: 50px;
  margin-bottom: 25px;
  padding-left:10px;
  font-size: 15px;
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.login-input:focus {
    border-color:#6e8095;
    outline: none;
  }
.login-button {
  width: 100%;
  height: 50px;
  padding: 0;
  font-size: 20px;
  color: #fff;
  text-align: center;
  background: #f0776c;
  border: 0;
  border-radius: 5px;
  cursor: pointer; 
  outline:0;
}

.login-lost
{
  text-align:center;
  margin-bottom:0px;
}

.login-lost a
{
  color:#666;
  text-decoration:none;
  font-size:13px;
}
        </style>
</head>
<body onload='document.loginForm.j_username.focus();'>
 
<form name='loginForm' action="<c:url value='j_spring_security_check' />" class="login"  method='post'>
 	<h1 class="login-title">Login</h1>
 	<div>
 	<%
	String errorString = (String)request.getAttribute("error");
	if(errorString != null && errorString.trim().equals("true")){
		out.println("Incorrect login name or password. Please retry.<br/><br/>");
		
	}
	%>
	</div>
    <div><label> User Name : <input type="text" value ="user1" name="j_username" class="login-input" placeholder="Username" autofocus/> </label></div>
    <div><label> Password: <input type="password" value ="password" name="j_password" class="login-input" placeholder="Password"/> </label></div>
    <div><input type="submit" value="Sign In" class="login-button" /></div>
</form>
</body>
</html>