import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const transferClient = backendApi('/api/v1/transfer')

export const transferApi = {
    makeTransfer(token, transferRequest) {
        console.log(`Making transfer to account with number ${transferRequest.accountNumber}`);
        return transferClient.post("/makeTransfer", transferRequest, {
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getTransfers(token, pageNumber, pageSize) {
        console.log(`Getting transfers for a user`);
        return transferClient.get("/getTransfers", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    }
};