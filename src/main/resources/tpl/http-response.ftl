<html>
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<head>
    <meta http-equiv="content-type" content="text/html; charset = UTF-8">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.3.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>

     <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
     <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" crossorigin="anonymous"></script>

     <link type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.18.1/styles/github.min.css" rel="stylesheet"/>
     <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.9/highlight.min.js"></script>
     <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.9/languages/bash.min.js"></script>
     <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.9/languages/json.min.js"></script>
     <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.9/languages/xml.min.js"></script>
     <script type="text/javascript">hljs.initHighlightingOnLoad();</script>

    <style>
        pre {
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<div><h4>Status code</h4> <#if data.responseCode??>
    <pre><code><b>${data.responseCode}</b></code></pre>
<#else>Unknown</#if></div>
<#if data.url??>

<div>
    <pre><code>${data.url}</code></pre>
</div></#if>

<#if (data.headers)?has_content>
<h4>Headers</h4>
<div>
    <#list data.headers as name, value>
        <div>
            <pre><code><b>${name}</b>: ${value}</code></pre>
        </div>
    </#list>
</div>
</#if>

<#if data.body??>
<h4>Body</h4>
<div>
    <pre><code>${data.body}</code></pre>
</div>
</#if>

<#if (data.cookies)?has_content>
<h4>Cookies</h4>
<div>
    <#list data.cookies as name, value>
        <div>
            <pre><code><b>${name}</b>: ${value}</code></pre>
        </div>
    </#list>
</div>
</#if>
</body>
</html>