"use strict";(self["webpackChunkleaf_blog_front"]=self["webpackChunkleaf_blog_front"]||[]).push([[334],{8334:function(s,t,a){a.r(t),a.d(t,{default:function(){return c}});var e=function(){var s=this,t=s._self._c;return t("div",{staticClass:"login-box-div"},[t("div",{staticClass:"juzhong"},[t("el-form",{ref:"forgetpassForm",staticClass:"demo-ruleForm",attrs:{model:s.forgetpassInfo,rules:s.rules}},[t("div",{staticClass:"login-box-img"},[t("img",{attrs:{src:a(8362),alt:""}})]),t("el-form-item",{attrs:{prop:"password"}},[t("div",{staticClass:"login-box-input"},[t("el-input",{attrs:{type:"password",placeholder:"NewPassword"},model:{value:s.forgetpassInfo.password,callback:function(t){s.$set(s.forgetpassInfo,"password",t)},expression:"forgetpassInfo.password"}})],1)]),t("el-form-item",{attrs:{prop:"email"}},[t("div",{staticClass:"login-box-input"},[t("el-input",{attrs:{placeholder:"Email"},model:{value:s.forgetpassInfo.email,callback:function(t){s.$set(s.forgetpassInfo,"email",t)},expression:"forgetpassInfo.email"}}),t("el-button",{staticClass:"btnsend",attrs:{disabled:s.btnDisable,type:"info"},on:{click:s.send}},[s._v(s._s(s.btnMsg))])],1)]),t("el-form-item",{attrs:{prop:"captcha"}},[t("div",{staticClass:"login-box-input"},[t("el-input",{attrs:{placeholder:"验证码"},model:{value:s.forgetpassInfo.captcha,callback:function(t){s.$set(s.forgetpassInfo,"captcha",t)},expression:"forgetpassInfo.captcha"}})],1)]),t("div",{staticClass:"login-box-span"},[t("span",{staticClass:"register-btn",on:{click:s.gotoLogin}},[s._v("返回登入")])]),t("div",{staticClass:"login-box-button"},[t("el-button",{staticClass:"login-button",attrs:{type:"info"},on:{click:s.submitBtn}},[s._v("更 改")])],1)],1)],1)])},i=[],r=(a(7658),{data(){var s=async(s,t,a)=>{if(""!=t){const s=await this.checkEmail();s?a():a(new Error("该邮箱还未被注册，请重新输入！"))}a()};return{rules:{password:[{required:!0,message:"请输入新密码",trigger:"blur"},{min:6,max:20,message:"长度在 6 到 20 个字符",trigger:["blur","change"]}],email:[{required:!0,message:"请输入邮箱",trigger:"blur"},{required:!0,type:"email",message:"请输入正确的邮箱",trigger:["blur","change"]},{validator:s,trigger:"blur"}]},forgetpassInfo:{password:"",email:"",captcha:""},btnMsg:"获取验证码",btnDisable:!1,time:59}},methods:{gotoLogin(){this.$router.push("/login")},async checkEmail(){const s=await this.$api.user.checkEmail({email:this.forgetpassInfo.email});return 200!==s.data.code},async send(){this.$refs.forgetpassForm.validate((async s=>{if(s){const s=await this.$api.user.sendEmail({email:this.forgetpassInfo.email});if(200===s.data.code){this.$message.success(s.data.msg),this.btnDisable=!0;let t=59,a=setInterval((()=>{this.btnMsg=t--+"秒后重试",0===t&&(this.btnDisable=!1,this.btnMsg="获取验证码",clearInterval(a))}),1e3)}else{this.$message.error(s.data.msg),this.btnDisable=!0;let t=59,a=setInterval((()=>{this.btnMsg=t--+"秒后重试",0===t&&(this.btnDisable=!1,this.btnMsg="获取验证码",clearInterval(a))}),1e3)}}}))},submitBtn(){this.$refs.forgetpassForm.validate((async s=>{if(s)if(null===this.forgetpassInfo.captcha||""===this.forgetpassInfo.captcha)this.$message.error("请输入验证码");else{const s=await this.$api.user.forgetPassword(this.forgetpassInfo);200===s.data.code?(this.$message.success(s.data.msg),this.$router.push("/login")):this.$message.error(s.data.msg)}}))}}}),o=r,l=a(1001),n=(0,l.Z)(o,e,i,!1,null,"6e9db761",null),c=n.exports},8362:function(s,t,a){s.exports=a.p+"img/更改密码.83494714.png"}}]);
//# sourceMappingURL=334.30895cde.js.map