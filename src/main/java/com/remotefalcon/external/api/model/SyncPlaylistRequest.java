package com.remotefalcon.external.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncPlaylistRequest {
  private List<SyncPlaylistDetails> playlists;
}
