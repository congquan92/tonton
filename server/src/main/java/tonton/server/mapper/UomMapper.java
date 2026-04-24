package tonton.server.mapper;

import tonton.server.model.Uom;
import tonton.server.controller.request.UomRequest;
import tonton.server.controller.response.UomResponse;

public final class UomMapper {
    private UomMapper() {
    }

    public static Uom toEntity(UomRequest request) {
        Uom entity = new Uom();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Uom entity, UomRequest request) {
        entity.setName(request.getName());
        entity.setSymbol(request.getSymbol());
    }

    public static UomResponse toResponse(Uom entity) {
        return UomResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .build();
    }
}