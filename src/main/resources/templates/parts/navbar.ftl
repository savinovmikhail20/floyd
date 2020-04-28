<#include "security.ftl">
<#import "login.ftl" as l>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">

<nav class="navbar navbar-expand-lg navbar-custom">
    <a class="navbar-brand" href="/"> Floyd  </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </#if>
            <#if user??>   <! user из секьюрити>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</a>
                </li>
            </#if>



            <li class="nav-item">
                <a class="nav-link" href="/game">Simple Game</a>
            </li>



        </ul>

        <div class="navbar-text mr-4">${name}</div>

        <#if user??>   <! user из секьюрити>
            <@l.logout />
        <#else>
            <@l.signIn />
        </#if>

    </div>
</nav>