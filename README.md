### Usage
#### template
`
<swipe-layout style="width:750;height:200;background-color:red" :ref="'item_${index}'">
        <bottom-view style="width:300;height:200;background-color:green" dragEdge="left">
            <text>bottom</text>
        </bottom-view>

       <bottom-view style="width:300;height:200;background-color:yellow" dragEdge="right" @click="bottomClick">
            <text>bottom</text>
        </bottom-view>

        <surface-view style="width:750;height:200;background-color:pink" @click="surfaceClick">
            <text>shdlsajkldkasldjklas</text>
        </surface-view>
    </swipe-layout>
`
  swipe-layout滑动组建父组件 其中只能包含两种子标签  bottom-view:隐藏的部分 surface-view 默认展示的部分
  注:子组件在swipe-layout中的顺序 必须surface-view在最后，bottom-view在surface-view之前，一个surface-view可以配合多个bottom-view

#### bottom-view
##### dragEdge 表示bottom-view 拖拽方向  支持参数：right left top bottom

#### swipe-layout
##### openEdge(params:String)  打开指定的bottom-view  参数支持 right left top bottom
`openEdge('right')`
#### close() 关闭打开的swipe-layout

#### Example 简单使用 将此段代码复制到eros项目中 即可查看效果
`<template>
    <div>
        <div @click="open">
            <text>OPEN</text>
        </div>
        <div>
            <text @click="close">CLOSE</text>
        </div>
        <list>
            <cell v-for="(item,index) in arr" :key="index">
                <swipe-layout style="width:750;height:200;background-color:red" :ref="'swipe'+index">
                    <bottom-view style="width:300;height:200;background-color:green" dragEdge="left">
                        <text>bottom</text>
                    </bottom-view>

                    <bottom-view style="width:300;height:200;background-color:yellow" dragEdge="right" @click="bottomClick">
                        <text>bottom</text>
                    </bottom-view>

                    <surface-view style="width:750;height:200;background-color:pink" @click="surfaceClick">
                        <text>shdlsajkldkasldjklas</text>
                    </surface-view>
                </swipe-layout>
            </cell>
        </list>    
    </div>
</template>
<script>
export default {

    data () {
        return {
          arr: [1,2,3,4,5,6,7,8,9,10],
        }
    },
    methods: {
        bottomClick(){
             this.$notice.toast({
              message: 'bottom Click'
            });
        },
        surfaceClick(){
             this.$notice.toast({
              message: 'surface Click'
            });
        },
        open(){
               this.$refs['swipe1'][0].openEdge("right")
        },
        close(){
               this.$refs['swipe1'][0].close()
        }
    },
}
</script>
`
#### Import 引入方法
1. 将SwipeSurfaceComponent SwipeLayoutComponent SwipeBottomComponent 放入app目录下对应位置
2. 在app/build.gradle中添加如下依赖:
`compile "com.daimajia.swipelayout:library:1.2.0@aar"`
3. 点击右上角Sync Now ，检查无报错即可。
