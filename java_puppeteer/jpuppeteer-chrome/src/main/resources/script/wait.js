async function wait(expression, timeout) {
    return await new Promise(async (resolve, reject) => {
        const waitInterval = 100;
        let isTimeout = false;
        const args = Array.prototype.slice.call(arguments, 2);
        const func = eval("(" + expression + ")");
        if (!func instanceof Function) {
            return reject(new Error("expression is not a vaild function"));
        }
        async function handler() {
            let result = undefined;
            try {
                if (func.constructor.name == "AsyncFunction") {
                    result = await func.apply(undefined, args);
                } else {
                    result = func.apply(undefined, args);
                }
                if (isTimeout) {
                    //执行超时, 不需要往后执行
                    return;
                }
                if (result === undefined) {
                    //未返回结果, 需要继续轮询
                    return setTimeout(handler, waitInterval);
                }
                //成功取得轮询结果
                return resolve(result);
            } catch (e) {
                //执行异常
                return reject(e);
            }
        }
        setTimeout(handler, waitInterval);
        setTimeout(function(){
            isTimeout = true;
            reject(new Error("timeout"));
        }, timeout);
    });
}