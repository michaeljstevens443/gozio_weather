package com.gozio.weather.weapose.presentation.model

import com.gozio.weather.weapose.data.model.response.Model

interface DataModelMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}
