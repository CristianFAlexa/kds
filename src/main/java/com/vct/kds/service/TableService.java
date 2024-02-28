package com.vct.kds.service;

import com.vct.kds.web.request.TableRequest;
import com.vct.kds.web.response.CurrentTableOrderResponse;

public interface TableService {

    void updateTableStatus(TableRequest tableRequest);

    CurrentTableOrderResponse getCurrentTableOrder(Long tableId);
}
