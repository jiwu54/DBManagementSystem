<template>
  <div
    style="
      width: 100%;
      height: 100vh;
      background-color: #021631;
      overflow: hidden;
    "
  >
    <div style="width: 400px; margin: 200px auto">
      <div
        style="
          color: #cccccc;
          font-size: 30px;
          text-align: center;
          padding: 30px 0;
        "
      >
        Welcome!
      </div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="Username">
            <template #prefix>
              <el-icon><user-filled /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            placeholder="Password"
            show-password
          >
            <template #prefix>
              <el-icon><key /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login"
            >Login</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="$router.push('/register')"
            >Register</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import { UserFilled, Key } from "@element-plus/icons-vue";

export default {
  components: {
    UserFilled,
    Key,
  },

  name: "Login",
  data() {
    return {
      form: {},
      rules: {
        username: [
          {
            required: true,
            message: "Please input username",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "Please input password",
            trigger: "blur",
          },
        ],
      },
    };
  },

  methods: {
    login() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          request.post("/api/user/login", this.form).then((res) => {
            if (res.code === "0") {
              this.$message({
                type: "success",
                message: "Login Successful",
              });
              this.$router.push("/"); //redirect to home
            } else {
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
          });
        }
      });
    },
  },
};
</script>

<style></style>
