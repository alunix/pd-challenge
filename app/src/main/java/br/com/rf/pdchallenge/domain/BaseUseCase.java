package br.com.rf.pdchallenge.domain;

import android.content.Context;

class BaseUseCase {

    protected Context mContext;

    public BaseUseCase(Context context) {
        this.mContext = context;
    }

}
