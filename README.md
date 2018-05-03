# SchPermission
一个自己定制的Android动态申请权限库


### 为什么要定制自己的动态申请权限库
Android6.0之后，Android系统就要求敏感权限需要动态申请。这位用户带来了很大的方便，也给开发者带来了更大的挑战。官方提供了相应的API供开发者使用，但是使用起来略显麻烦。市场上也有很多开源权限申请库，之前有使用rxPermission，这次也有参考rxPermission的代码。使用开源库固然很方便，但是我觉得，开源的意义在于提供一种思路，给其他同行一个借鉴，大家最好还是要会写自己的库，哪怕搓一点也没关系。

### 动态申请权限的思路
- 第一种：官方的写法是在当前Activity申请权限，在当前Activity回调结果。这种写法很标准，但是很繁琐，写起来非常不方便。 
- 第二种：之前看到一个同事写的库，他是跳到一个新的Activity，然后申请权限，在该Activity监听回调。这种方法达到了方便使用的效果，但是体验不是很好。我写的第一个版本就是这个方法。
- 第三种：在当前Activity添加一个Fragment，在该Fragment中申请权限，并在该Fragment中监听返回结果。这是rxPermission的思路，我觉得这是三种方法中最好的方法，最终版本也是采用的这个方法。

### 代码实现步骤
- 首先，写一个入口类：SchPermission，构造函数传入activity，创建fragment添加到该activity。 
- 然后，创建一个Fragment类：SchPermissionFragment。具体的申请权限申请及申请结果回调都在该fragment中。 
- 为了防止同时多次调用申请权限接口，导致回调错乱，每次申请权限的requestCode都不一样，创建一个HashMap存储requestCode和callback，一一对应，防止回调错乱。

### 使用例子
```
  SchPermission mSchPermission = new SchPermission(this);
  mSchPermission
                          .request(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                  new
                                          IPermissionCallback() {
                                              @Override
                                              public void onPermissionResult(int requestCode,
                                                                             @NonNull String[] permissions,
                                                                             @NonNull int[] grantResults) {
                                                  StringBuffer sb = new StringBuffer();
                                                  for (String str : permissions) {
                                                      sb.append(" ");
                                                      sb.append(str);
                                                  }
                                                  StringBuffer sb2 = new StringBuffer();
                                                  for (int i : grantResults) {
                                                      sb2.append(" ");
                                                      sb2.append(i);
                                                  }
                                                  Log.i(TAG, "permissions=" + sb.toString() + ",grantResults=" + sb2
                                                          .toString() + ",shouldShowRequestPermissionRationale=");

                                              }
                                          });
    
```

### 后记
写的比较简单，请大家多多指教。