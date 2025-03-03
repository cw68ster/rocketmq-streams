/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.streams.script.function.impl.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.rocketmq.streams.common.context.IMessage;
import org.apache.rocketmq.streams.script.annotation.Function;
import org.apache.rocketmq.streams.script.annotation.FunctionMethod;
import org.apache.rocketmq.streams.script.annotation.FunctionParamter;
import org.apache.rocketmq.streams.script.context.FunctionContext;
import org.apache.rocketmq.streams.script.utils.FunctionUtils;

@Function
public class CurrentTimestampFunction {

    /**
     * 获取当前时间戳
     *
     * @param message
     * @param context
     * @return
     */
    @FunctionMethod(value = "curstamp", alias = "timestamp", comment = "生成时间戳")
    public Long curstamp(IMessage message, FunctionContext context) {
        Timestamp timestamp = null;
        timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    @FunctionMethod(value = "curstamp", alias = "timestamp", comment = "生成指定时间的时间戳")
    public Long convert(IMessage message, FunctionContext context,
                        @FunctionParamter(value = "string", comment = "标准时间格式的时间") String dateTime) {
        String dateTimeStr = FunctionUtils.getValueString(message, context, dateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateTime == null) {
            return null;
        }
        try {
            Date dateSource = dateFormat.parse(dateTimeStr);
            return dateSource.getTime();
        } catch (ParseException e) {
            throw new RuntimeException("timestamp函数执行错误", e);
        }
    }
}
