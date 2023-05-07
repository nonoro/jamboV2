package backend.jambo.web;

import backend.jambo.application.MemberService;
import backend.jambo.domain.request.MemberRequest;
import backend.jambo.domain.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberResponse> saveMember(@RequestBody MemberRequest request) {
        MemberResponse savedMember = memberService.save(request);
        return ResponseEntity.created(URI.create("/member/" + savedMember.id())).body(savedMember);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMember(memberId));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> findMembers() {
        return ResponseEntity.ok(memberService.findAllRegisteredMember());
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }
}
