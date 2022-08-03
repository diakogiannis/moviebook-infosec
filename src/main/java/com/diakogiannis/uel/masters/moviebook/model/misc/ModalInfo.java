package com.diakogiannis.uel.masters.moviebook.model.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ModalInfo {
    private @NonNull String title;
    private @NonNull String info;
}
