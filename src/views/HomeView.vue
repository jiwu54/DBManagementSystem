<template>
  <div style="paddiong: 10px">
    <div style="margin: 10px">
      <el-button type="primary" @click="add">Add</el-button>
      <el-button type="primary">Import</el-button>
      <el-button type="primary">Export</el-button>
      <el-button type="primary">Delete</el-button>
    </div>

    <div style="margin: 10px">
      <el-input
        v-model="search"
        placeholder="Please Enter Keyword"
        style="width: 20%"
        clearable
      />
      <el-button type="primary" style="margin-left: 5px" @click="load"
        >Search</el-button
      >
    </div>

    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable />
      <el-table-column prop="username" label="Username" />
      <el-table-column prop="nickName" label="Nickname" />
      <el-table-column prop="age" label="Age" />
      <el-table-column prop="gender" label="Gender" />
      <el-table-column prop="address" label="Address" />

      <el-table-column fixed="right" label="Operations">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">Edit</el-button>
          <el-popconfirm
            title="Are you sure to delete this?"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button size="mini" type="danger"> Delete</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px">
      <el-pagination
        v-model:currentPage="currentPage4"
        v-model:page-size="pageSize4"
        :page-sizes="[5, 10, 20]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="UserName">
            <el-input v-model="form.username" style="width: 80%" />
          </el-form-item>
          <el-form-item label="Nickname">
            <el-input v-model="form.nickName" style="width: 80%" />
          </el-form-item>
          <el-form-item label="Age">
            <el-input v-model="form.age" style="width: 80%" />
          </el-form-item>
          <el-form-item label="Gender">
            <el-radio v-model="form.gender" label="Male">Male</el-radio>
            <el-radio v-model="form.gender" label="Female">Female</el-radio>
            <el-radio v-model="form.gender" label="Other">Other</el-radio>
          </el-form-item>
          <el-form-item label="Address">
            <el-input
              type="textarea"
              v-model="form.address"
              style="width: 80%"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="save">Confirm</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "HomeView",
  components: {},

  data() {
    return {
      form: {},
      dialogVisible: false,
      currentPage4: 1,
      pageSize4: 10,
      totalPage: 0,
      search: "",
      tableData: [],
    };
  },

  created() {
    this.load();
  },

  methods: {
    load() {
      request
        .get("/api/user", {
          params: {
            pageNum: this.currentPage4,
            pageSize: this.pageSize4,
            search: this.search,
          },
        })
        .then((res) => {
          console.log(res);
          (this.tableData = res.data.records),
            (this.totalPage = res.data.total);
        });
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    save() {
      if (this.form.id) {
        request.put("/api/user", this.form).then((res) => {
          console.log(res);
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "Successful Update",
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
          this.load();
          this.dialogVisible = false;
        });
      } else {
        request.post("/api/user", this.form).then((res) => {
          console.log(res);
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "Successful Add",
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }

          this.load();
          this.dialogVisible = false;
        });
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete(id) {
      request.delete("/api/user/" + id).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "Successful Delete",
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
        this.load();
      });
    },
    handleSizeChange(pageSize) {
      this.pageSize4 = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage4 = pageNum;
      this.load();
    },
  },
};
</script>
