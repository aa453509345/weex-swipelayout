### Usage
#### template
 
```
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
```
##### swipe-layout是滑动父组件 其中只能包含两种子标签  bottom-view（隐藏的部分） surface-view （默认展示的部分）
      注:子组件在swipe-layout中的顺序 必须surface-view在最后，bottom-view在surface-view之前，一个surface-view可以配合多个bottom-view

#### bottom-view
##### 属性：dragEdge 
    表示bottom-view 拖拽方向  支持参数：right left top bottom

#### swipe-layout
##### 方法 openEdge(params:String)  
    打开指定的bottom-view  参数支持 right left top bottom 例如：openEdge('right')即为打开右边的bottom-view
#### 方法close() 
    关闭swipe-layout

#### Example 
    简单使用 将此段代码复制到eros项目中 即可查看效果
```
<template>
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
```
#### Import 引入方法
1. 将SwipeSurfaceComponent SwipeLayoutComponent SwipeBottomComponent 放入app目录下对应位置
2. 在App中添加如下代码
```
  try {
            WXSDKEngine.registerComponent("bottom-view", SwipeBottomComponent.class);
            WXSDKEngine.registerComponent("swipe-layout", SwipeLayoutComponent.class);
            WXSDKEngine.registerComponent("surface-view", SwipeSurfaceComponent.class);

        } catch (WXException e) {
            e.printStackTrace();
        }
```
3. 在app/build.gradle中添加如下依赖:
`compile "com.daimajia.swipelayout:library:1.2.0@aar"`
4. 点击右上角Sync Now ，检查无报错即可。

#### 效果查看swipe.gif

#### drag-list
    可拖拽条目的list 继承list所有现有功能
##### 拓展属性
##### 1.draggable {bool}
    drag-list是否开启拖拽功能 默认值为false
##### 2.dragTriggerType{string}
    dragTriggerType表示触发拖拽的方式，目前支持两种方式'pan'和'longpress'
    当dragTriggerType=longpress时 长按列表条目触发拖拽事件
    当dragTriggerType=pan时，表示拖拽指定组件时触发拖拽。使用pan模式时，需要在list<cell>中的任意你需要子组件添加属性dragAnchor="true",才能生效。带有dragAnchor属性的标签即为触发拖拽的锚点。
 
```
 <cell v-for="(num, index) in arr" :key="index"  @click="cellClick">
                <div>
              <swipe-layout style="width:750;height:100;background-color:pink">
              <bottom-view style="width:200;height:100;background-color:yellow" dragEdge="right">
                <div>
                <image style="width:100px; height:100px;margin-left:50px;" src="bmlocal://assets/demo.jpg" dragAnchor="true" @click="imageClick"></image>
                </div>
                </bottom-view>
                     <surface-view style="width:750;height:100;background-color:pink">
                         <div-gesture  @ondoubleclick="ondoubleclick(index)" forceTouch="true" @onclick="onclick(index)" @onlongpress="onlongpress(index)">
                             <!-- <text @click="textClick" style="width:200;background-color:red">{{`这是第carry${num}条数据`}}</text> -->
                          <wxc-cell :title="`这是第carry${num}条数据`"
                          :has-arrow="false"
                           :has-top-border="true"
                           :style="{'background-color': curIndex == index ? 'red':''}"
                          />
                         </div-gesture>
                    </surface-view>
                </swipe-layout>
                </div>
```
以当前cell为例,其中image被dragAnchor="true" ,即当触摸image标签时触发拖拽事件

##### vibrate{bool}
    拖拽开始时是否震动反馈 默认值为true

##### 拓展事件
##### dragstart
    drag-list在拖拽开始时的事件回调
    params:
    {
        fromIndex:条目原本的index
        timestamp:时间戳
    }
##### dragend
    drag-list在拖拽结束时的事件回调
    params:
    {
        fromIndex:条目原本的index
        toIndex:条目拖拽后的index
        timestamp:时间戳
    }

#### div-gesture
   考虑到将长按事件直接放在cell可能会引起的手势冲突和扩展性问题，开发了可以反馈单击，双击，长按事件的容器：div-gesture

##### 支持属性
##### forceTouch{true}
     在某些特殊情况下，需要外层div-gesture直接拦截事件而不将事件再向子传递，将此属性设置为true。默认值为flase
##### 事件
##### 1.onclick
    单击事件
##### 2.onlongpress
    长按事件
##### 3.ondoubleclick
    双击事件

##### 例子
```
   <surface-view style="width:750;height:100;background-color:pink">
                         <div-gesture  @ondoubleclick="ondoubleclick(index)" forceTouch="true" @onclick="onclick(index)" @onlongpress="onlongpress(index)">
                             <!-- <text @click="textClick" style="width:200;background-color:red">{{`这是第carry${num}条数据`}}</text> -->
                          <wxc-cell :title="`这是第carry${num}条数据`"
                          :has-arrow="false"
                           :has-top-border="true"
                           :style="{'background-color': curIndex == index ? 'red':''}"
                          />
                         </div-gesture>
                    </surface-view>
```
该例中请注意 
1.surface-view本身是一个内部由滑动控件实现的组件，本身需要消费事件，如果将div-gesture作为    surface-view的父组件，会造成手势冲突。所以div-gesture最好作为滑动控件的子组件

2.wxc-cell时weex-ui的一个组件，内部消费了事件，但在本例中，需要监听整个surface-view的点击长按双击事件，故将forceTouch="true"。

#### 集成方式
1.在app/build.gradle中添加如下依赖:
`compile "com.daimajia.swipelayout:library:1.2.0@aar"`(如上次已有，跳过)

2.将extend包下所有的类copy

3. 在App中添加如下代码(直接替换之前的)
```
     try {
            WXSDKEngine.registerComponent("bottom-view", SwipeBottomComponent.class);
            WXSDKEngine.registerComponent("swipe-layout", SwipeLayoutComponent.class);
            WXSDKEngine.registerComponent("surface-view", SwipeSurfaceComponent.class);
            WXSDKEngine.registerComponent(DraggableListComponent.class, false, "drag-list");
            WXSDKEngine.registerComponent("div-gesture", GestureLayoutComponent.class);
            WXSDKEngine.registerDomObject("drag-list", WXListDomObject.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
```

4.点击右上角Sync Now ，检查无报错即可。


#### Demo代码
```
<template>
    <div>
        <text style="height:50px;text-align:center;width:750;border:10;margin-top:20" @click="setDraggable">开启拖拽模式</text>
        <drag-list ref="list" :show-scrollbar="false" :showRefresh="true" @refresh="onrefresh" dragTriggerType="pan" :draggable="drage" @dragstart="onStart" @dragend="onEnd">
            <cell v-for="(num, index) in arr" :key="index"  @click="cellClick">
                <div>
              <swipe-layout style="width:750;height:100;background-color:pink">
              <bottom-view style="width:200;height:100;background-color:yellow" dragEdge="right">
                <div>
                <image style="width:100px; height:100px;margin-left:50px;" src="bmlocal://assets/demo.jpg" dragAnchor="true" @click="imageClick"></image>
                </div>
                </bottom-view>
                     <surface-view style="width:750;height:100;background-color:pink">
                         <div-gesture  @ondoubleclick="ondoubleclick(index)" forceTouch="true" @onclick="onclick(index)" @onlongpress="onlongpress(index)">
                             <!-- <text @click="textClick" style="width:200;background-color:red">{{`这是第carry${num}条数据`}}</text> -->
                          <wxc-cell :title="`这是第carry${num}条数据`"
                          :has-arrow="false"
                           :has-top-border="true"
                           :style="{'background-color': curIndex == index ? 'red':''}"
                          />
                         </div-gesture>
                    </surface-view>
                </swipe-layout>
                </div>
            </cell>
        </drag-list>
        <div class="touch-bar" :style="{'height': touchBarHeight}">
        </div>
    </div>
</template>
<script>
import { WxcCell } from 'weex-ui'
export default {
    components: { WxcCell },
    data () {
        return {
            arr: [],
            arr1: [],
            touchBarHeight: weex.config.eros.touchBarHeight ? weex.config.eros.touchBarHeight : 0,
            drage:false,
            curIndex: -1
        }
    },
    created () {
        for (let i = 0; i < 20; i++) {
            this.arr.push(i + 1)
            this.arr1.push( i+ 1)
        }
    },
    methods: {
        onrefresh () {
            setTimeout(() => {
                this.$refs['list'].refreshEnd()
                this.$notice.toast({
                    message: 'refreshEnd'
                });
            }, 1000)
        },
        setDraggable(){
             this.$notice.toast({
                    message: '开启拖拽模式'
                });
             this.drage=true
        },
        onStart(index){
            // this.curIndex = index
              this.$notice.toast({
                    message: '拖拽开始了'
                });
        },
        onEnd(aaa){
            
            this.curIndex = -1
            
            let a1 = this.arr1.splice(aaa.fromIndex,1)


            this.arr1.splice(aaa.toIndex,0,a1)

            console.log("xxxxxxxxx",this.arr1)

             this.$notice.toast({
                    message: '拖拽结束了'
                });
        },
        cellClick(){
           this.$notice.toast({
                    message: 'cell被点击'
                });  
        },
        imageClick(){
              this.$notice.toast({
                    message: 'image被点击'
                });  
        },
        textClick(){
             this.$notice.toast({
                    message: '文字被点击了'
                });  
        }
        ,surfaceClick(){
             this.$notice.toast({
                    message: 'dblclickdblclick'
                });  
        },
        ondoubleclick(index){
            this.$notice.toast({
                    message: '双击第'+index+'项'
                });  
        },
        onclick(index){
             this.$notice.toast({
                    message: '点击第'+index+'项'
                });  
        },
        onlongpress(index){
            
             this.$notice.toast({
                    message: '长按第'+index+'项'
                });  
        }
    }
}

</script>
<style lang="sass" scoped>
@import 'src/js/css/base';
.container {
    width: 700px;
    margin: 25px;
}
```

