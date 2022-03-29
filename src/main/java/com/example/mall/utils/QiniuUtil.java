package com.example.mall.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;

import lombok.extern.slf4j.Slf4j;

/**
 * 七牛云工具类
 *
 * @className com.example.mall.utils.QiniuUtil
 * @date 2022/03/29
 * @version V1.0
 */
@Slf4j
public class QiniuUtil {

  /** 在七牛注册后获得的accessKey和secretKey */
  private static final String ACCESS_KEY = "lzc2ZMirhWkxylQPQJhnnBW1oI4dRvZmlKbeutlJ";

  private static final String SECRET_KEY = "lOhP2EqGBawrBG2-XLgO7vkDtQ6dA7Mwkg2UFNgi";
  /** 七牛空间名 */
  private static final String BUCKET = "trainingmanager";
  /** 本地域名，与文件名拼接完成url */
  private static final String DOMAIN = "https://tm.51jiama.com/";

  /**
   * 获取覆盖同名文件的上传凭证
   *
   * @param key 序列
   * @return {@link String }
   * @since 2022/03/29
   */
  public static String getToken(String key) {
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 生成覆盖相同key文件的上传Token
    return auth.uploadToken(BUCKET, key, 36000000, null, true);
  }

  /**
   * 获取普通上传凭证
   *
   * @return {@link String }
   * @since 2022/03/29
   */
  public static String getUploadToken() {
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    return auth.uploadToken(BUCKET, null, 36000000, null, true);
  }

  /**
   * 删除七牛中指定key的文件
   *
   * @param key 序列
   * @return boolean
   * @since 2022/03/29
   */
  public static boolean delFile(String key) {
    // 构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Region.region0());
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      bucketManager.delete(BUCKET, key);
      return true;
    } catch (QiniuException ex) {
      // 如果遇到异常，说明删除失败
      log.error("失败: " + ex.code() + "|" + ex.response.toString());
      return false;
    }
  }

  /**
   * 批量删除七牛文件，参数为key序列
   *
   * @param keyList 序列列表
   * @return boolean
   * @since 2022/03/29
   */
  public static boolean delFileList(String[] keyList) {
    Configuration cfg = new Configuration(Region.region0());
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
      batchOperations.addDeleteOp(BUCKET, keyList);
      Response response = bucketManager.batch(batchOperations);
      BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
      for (int i = 0; i < keyList.length; i++) {
        BatchStatus status = batchStatusList[i];
        String key = keyList[i];
        log.info("删除" + key + ":" + status.code);
        if (status.code == 200) {
          log.info("删除成功");
        } else {
          log.info("删除失败" + status.data.error);
        }
      }
      return true;
    } catch (QiniuException ex) {
      log.error("失败" + ex.response.toString());
      return false;
    }
  }
}
