import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const messageClient = backendApi("/api/v1/message");

export const messageApi = {
    createDateMessage(token, dateMessage) {
        console.log(`Creating date message ${dateMessage}`);
        return messageClient.post("/createDateMessage",
            dateMessage, {
                headers: {
                    Authorization: bearerAuth(token)
                }
            });
    },
    createValueMessage(token, valueMessage) {
        console.log(`Creating value message ${valueMessage}`);
        return messageClient.post("/createValueMessage",
            valueMessage, {
                headers: {
                    Authorization: bearerAuth(token)
                }
            });
    },
    getDateMessages(token, pageNumber, pageSize) {
        console.log("Getting date messages for a user");
        return messageClient.get("/getDateMessages", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getValueMessages(token, pageNumber, pageSize) {
        console.log("Getting value messages for a user");
        return messageClient.get("/getValueMessages", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    deleteDateMessage(token, id) {
        console.log(`Deleting date message with id ${id}`);
        return messageClient.delete("/deleteDateMessages", {
            params: {
                id: id
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    deleteValueMessage(token, id) {
        console.log(`Deleting value message with id ${id}`);
        return messageClient.delete("/deleteValueMessages", {
            params: {
                id: id
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
};