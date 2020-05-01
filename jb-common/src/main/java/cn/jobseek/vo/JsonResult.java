package cn.jobseek.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -3270477101415407533L;
    private boolean isok;
    private int code;
    private String message;
    private Object data;


    public static JsonResult success(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(true);
        jsonResult.setCode(200);
        jsonResult.setMessage("success");
        return jsonResult;
    }

    public static JsonResult message(String msg){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(true);
        jsonResult.setCode(200);
        jsonResult.setMessage(msg);
        return jsonResult;
    }
    public static JsonResult success(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(true);
        jsonResult.setCode(200);
        jsonResult.setMessage("success");
        jsonResult.setData(data);
        return jsonResult;
    }


    public static JsonResult fail(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(false);
        jsonResult.setCode(500);
        return jsonResult;
    }

    public static JsonResult fail(Throwable t){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(false);
        jsonResult.setCode(500);
        jsonResult.setMessage(t.getMessage());
        return jsonResult;
    }

    public static JsonResult fail(Throwable t,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setIsok(false);
        jsonResult.setCode(500);
        jsonResult.setMessage(message);
        return jsonResult;
    }




}
