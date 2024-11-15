package com.remotefalcon.external.api.response;

import com.remotefalcon.library.enums.ShowRole;
import com.remotefalcon.library.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowResponse {
    private String showName;
    private String showSubdomain;
    private Preference preferences;
    private List<Sequence> sequences;
    private List<SequenceGroup> sequenceGroups;
    private List<PsaSequence> psaSequences;
    private List<Request> requests;
    private List<Vote> votes;
    private String playingNow;
    private String playingNext;
    private String playingNextFromSchedule;
}
