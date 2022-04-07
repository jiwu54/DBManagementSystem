<template>
  <div>
    <Header />

    <div style="display: flex">
      <NavBar />
      <router-view style="flex: 1" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import NavBar from "@/components/NavBar";

export default {
  name: "Layout",
  components: {
    Header,
    NavBar,
  },
};
</script>

<style></style>
