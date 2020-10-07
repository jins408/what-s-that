<template>
<!-- <div>
    <p>footer</p>
</div> -->
  <div class="footer" style="background-color:#424242;">
    <div class="container">       
        <div class="mb-2 d-flex justify-content-end" style="color:white">
          <span class >이용약관</span>
          <span class="ml-2 mr-2">|</span>
          <span class>개인정보처리방침</span>
          <span class="ml-2 mr-2">|</span>
          <span class>회사소개</span>
        </div>
        <div  class="d-flex justify-content-end" style="font-size:0.8rem; color:white">
          <p class="mb-3">Made by 전병규 김석환 윤영현 장진우 김진실</p>
        </div>
       
      </div>

  </div>

</template>

<script>


export default {
    name:'Footer',

    data:function () {
        return {
            
        }
    }
}
</script>

<style>
.footer{
  height: auto;
  padding-top: 30px;
  padding-bottom: 30px;
}
</style>