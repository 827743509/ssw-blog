<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">Blog Admin</h3>
        <p class="subtitle">Sign in to continue</p>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-button :loading="loading" type="primary" class="login-button" @click.prevent="handleLogin">Login</el-button>

      <div class="tips">
        <span>username: admin</span>
        <span> password: any</span>
      </div>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'admin',
        password: '111111'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 48px;
    width: calc(100% - 50px);

    .el-input__wrapper {
      height: 48px;
      padding: 0;
      background: transparent;
      box-shadow: none;
    }

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 48px;
      caret-color: $cursor;
      font-size: 15px;
      line-height: 48px;

      &::placeholder {
        color: rgba(255, 255, 255, 0.45);
      }

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(255, 255, 255, 0.08);
    border-radius: 8px;
    color: #454545;
    margin-bottom: 24px;

    .el-form-item__content {
      display: flex;
      align-items: center;
      min-height: 48px;
      line-height: 48px;
    }

    &:focus-within {
      border-color: rgba(64, 158, 255, 0.8);
      background: rgba(255, 255, 255, 0.11);
      box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.14);
    }
  }
}
</style>

<style lang="scss" scoped>
$bg:#223042;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background:
    radial-gradient(circle at 20% 20%, rgba(64, 158, 255, 0.18), transparent 28%),
    linear-gradient(135deg, #1f2d3d 0%, $bg 52%, #17212f 100%);
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;

  .login-form {
    position: relative;
    width: 420px;
    max-width: calc(100vw - 48px);
    padding: 44px 40px 36px;
    margin: 0;
    overflow: visible;
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    background: rgba(16, 24, 36, 0.72);
    box-shadow: 0 24px 60px rgba(0, 0, 0, 0.26);
    backdrop-filter: blur(12px);
  }

  .tips {
    display: flex;
    justify-content: center;
    gap: 18px;
    font-size: 13px;
    color: rgba(255, 255, 255, 0.68);
    margin-bottom: 0;

    span {
      &:first-of-type {
        margin-right: 0;
      }
    }
  }

  .svg-container {
    padding: 0 10px 0 16px;
    color: rgba(255, 255, 255, 0.5);
    vertical-align: middle;
    width: 50px;
    display: inline-block;
    line-height: 48px;
    text-align: center;
  }

  .title-container {
    position: relative;
    margin-bottom: 34px;

    .title {
      font-size: 30px;
      color: $light_gray;
      margin: 0;
      text-align: center;
      font-weight: bold;
      line-height: 1.2;
    }

    .subtitle {
      margin: 10px 0 0;
      color: rgba(255, 255, 255, 0.55);
      font-size: 14px;
      line-height: 20px;
      text-align: center;
    }
  }

  .show-pwd {
    position: absolute;
    right: 14px;
    top: 0;
    font-size: 16px;
    color: rgba(255, 255, 255, 0.5);
    cursor: pointer;
    user-select: none;
    line-height: 48px;
    transition: color 0.2s;

    &:hover {
      color: #fff;
    }
  }

  .login-button {
    width: 100%;
    height: 44px;
    margin-bottom: 26px;
    border: 0;
    border-radius: 8px;
    font-size: 15px;
    font-weight: 600;
    background: linear-gradient(135deg, #409eff 0%, #2f7ff0 100%);
    box-shadow: 0 10px 24px rgba(64, 158, 255, 0.25);
  }

  @media (max-width: 480px) {
    .login-form {
      padding: 36px 24px 30px;
    }

    .tips {
      flex-direction: column;
      gap: 6px;
      align-items: center;
    }
  }
}
</style>
