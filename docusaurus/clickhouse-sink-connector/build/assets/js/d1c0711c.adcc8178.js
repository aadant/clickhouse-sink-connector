"use strict";(self.webpackChunkclickhouse_sink_connector=self.webpackChunkclickhouse_sink_connector||[]).push([[47],{3905:(e,t,r)=>{r.d(t,{Zo:()=>u,kt:()=>b});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function a(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function s(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},i=Object.keys(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var c=n.createContext({}),l=function(e){var t=n.useContext(c),r=t;return e&&(r="function"==typeof e?e(t):a(a({},t),e)),r},u=function(e){var t=l(e.components);return n.createElement(c.Provider,{value:t},e.children)},p="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,i=e.originalType,c=e.parentName,u=s(e,["components","mdxType","originalType","parentName"]),p=l(r),m=o,b=p["".concat(c,".").concat(m)]||p[m]||d[m]||i;return r?n.createElement(b,a(a({ref:t},u),{},{components:r})):n.createElement(b,a({ref:t},u))}));function b(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var i=r.length,a=new Array(i);a[0]=m;var s={};for(var c in t)hasOwnProperty.call(t,c)&&(s[c]=t[c]);s.originalType=e,s[p]="string"==typeof e?e:o,a[1]=s;for(var l=2;l<i;l++)a[l]=r[l];return n.createElement.apply(null,a)}return n.createElement.apply(null,r)}m.displayName="MDXCreateElement"},6522:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>c,contentTitle:()=>a,default:()=>d,frontMatter:()=>i,metadata:()=>s,toc:()=>l});var n=r(7462),o=(r(7294),r(3905));const i={},a=void 0,s={unversionedId:"doc/Troubleshooting",id:"doc/Troubleshooting",title:"Troubleshooting",description:"Caused by java.sql.SQLSyntaxErrorException: Access denied; you need (at least one of)",source:"@site/docs/doc/Troubleshooting.md",sourceDirName:"doc",slug:"/doc/Troubleshooting",permalink:"/doc/Troubleshooting",draft:!1,tags:[],version:"current",frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Unit tests",permalink:"/doc/TESTING"},next:{title:"Architecture",permalink:"/doc/architecture"}},c={},l=[{value:"Caused by: io.debezium.DebeziumException: java.sql.SQLSyntaxErrorException: Access denied; you need (at least one of)",id:"caused-by-iodebeziumdebeziumexception-javasqlsqlsyntaxerrorexception-access-denied-you-need-at-least-one-of",level:3},{value:"the SUPER, REPLICATION CLIENT privilege(s) for this operation",id:"the-super-replication-client-privileges-for-this-operation",level:3},{value:"Debezium error: Handle Unable to register metrics as an old set with the same name exists",id:"debezium-error-handle-unable-to-register-metrics-as-an-old-set-with-the-same-name-exists",level:4}],u={toc:l},p="wrapper";function d(e){let{components:t,...r}=e;return(0,o.kt)(p,(0,n.Z)({},u,r,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h3",{id:"caused-by-iodebeziumdebeziumexception-javasqlsqlsyntaxerrorexception-access-denied-you-need-at-least-one-of"},"Caused by: io.debezium.DebeziumException: java.sql.SQLSyntaxErrorException: Access denied; you need (at least one of)"),(0,o.kt)("h3",{id:"the-super-replication-client-privileges-for-this-operation"},"the SUPER, REPLICATION CLIENT privilege(s) for this operation"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"mysql> GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'user' IDENTIFIED BY 'password';\n\n")),(0,o.kt)("h4",{id:"debezium-error-handle-unable-to-register-metrics-as-an-old-set-with-the-same-name-exists"},"Debezium error: Handle Unable to register metrics as an old set with the same name exists"),(0,o.kt)("p",null,"For every connector the ",(0,o.kt)("inlineCode",{parentName:"p"},"database.server.name")," should be unique."))}d.isMDXComponent=!0}}]);