package backend.jambo.application;

import backend.jambo.domain.Member;
import backend.jambo.domain.Status;
import backend.jambo.domain.request.MemberRequest;
import backend.jambo.domain.response.MemberResponse;
import backend.jambo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse save(MemberRequest request) {
        memberRepository.findById(request.id()).ifPresent(member -> {
            throw new IllegalArgumentException(String.format("해당 유저 %d는 이미 가입되어 있습니다.", request.id()));
        });

        Member member = Member.of(request.id(), request.nickname());
        memberRepository.save(member);
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(long id) {
        return MemberResponse.of(memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("존재하지 않는 유저 %d 입니다.", id))));
    }

    public List<MemberResponse> findAllRegisteredMember() {
        return memberRepository.findAllRegisteredMember(Status.REGISTER)
                .stream()
                .map(MemberResponse::of)
                .toList();
    }

    @Transactional
    public MemberResponse deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 유저 %d 입니다.", id)));

        member.updateStatus();

        return MemberResponse.of(member);
    }

    @Transactional
    public MemberResponse updateMember(Long id, String nickname) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 유저 %d 입니다.", id)));

        member.updateNickname(nickname);

        return MemberResponse.of(member);
    }
}
