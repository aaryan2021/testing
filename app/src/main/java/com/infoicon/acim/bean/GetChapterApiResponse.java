package com.infoicon.acim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sumit on 1/9/17.
 */

public class GetChapterApiResponse {


    /**
     * success : true
     * msg : Data fetch successfully
     * data : {"text":{"text":["Foreword","Introduction"]},"chapters":[{"chaptername":"Chapter 1 Introduction to Miracles:","topics":[{"id":"TxtChap-1-1","name":"Principles of Miracles"},{"id":"TxtChap-1-2","name":"II. Distortions of Miracle Impulses"}]},{"chaptername":"Chapter 2:The Illusion of Separation","topics":[{"id":"TxtChap-2-1","name":"I. Introduction"},{"id":"TxtChap-2-2","name":"II.  The Reinterpretation of Defenses"},{"id":"TxtChap-2-3","name":"III.  Healing as Release from Fear"},{"id":"TxtChap-2-4","name":"IV.  Fear as Lack of Love"},{"id":"TxtChap-2-5","name":"V. The Correction for Lack of Love"},{"id":"TxtChap-2-6","name":"VI. The Meaning of the Last Judgment"}]},{"chaptername":"Chapter 3 Retraining the Mind","topics":[{"id":"TxtChap-3-1","name":"I. Introduction"},{"id":"TxtChap-3-2","name":"II. Special Principles For Miracle Workers"},{"id":"TxtChap-3-3","name":"III. Atonement Without Sacrifice"},{"id":"TxtChap-3-4","name":"IV. Miracles as Accurate Perception"},{"id":"TxtChap-3-5","name":"V. Perception Versus Knowledge"},{"id":"TxtChap-3-6","name":"VI. Conflict and the Ego"},{"id":"TxtChap-3-7","name":"VII. The Loss of Certainty"},{"id":"TxtChap-3-8","name":"VIII. Judgment and the Authority Problem"},{"id":"TxtChap-3-9","name":"IX. Creating Versus the Self-Image"}]},{"chaptername":"Chapter 4 The Root of All Evil","topics":[{"id":"TxtChap-4-1","name":"I. Introduction"},{"id":"TxtChap-4-2","name":"II. Right Teaching and Right Learning"},{"id":"TxtChap-4-3","name":"III. The Ego and False Autonomy"},{"id":"TxtChap-4-4","name":"IV. Love Without Conflict"},{"id":"TxtChap-4-5","name":"V. The Escape From Fear"},{"id":"TxtChap-4-6","name":"VI. The Ego-Body Illusion"},{"id":"TxtChap-4-7","name":"VII. The Constant State"},{"id":"TxtChap-4-8","name":"VIII. Creation and Communication"},{"id":"TxtChap-4-9","name":"IX. True Rehabilitation"}]},{"chaptername":"Chapter 5 Healing and Wholeness","topics":[{"id":"TxtChap-5-1","name":"I. Introduction"},{"id":"TxtChap-5-2","name":"II. Healing As Joining"},{"id":"TxtChap-5-3","name":"III. The Mind of the Atonement"},{"id":"TxtChap-5-4","name":"IV. The Voice For God"},{"id":"TxtChap-5-5","name":"V. The Guide to Salvation"},{"id":"TxtChap-5-6","name":"VI. Therapy and Teaching"},{"id":"TxtChap-5-7","name":"VII. The Two Decisions"},{"id":"TxtChap-5-8","name":"VIII. Time and Eternity"},{"id":"TxtChap-5-9","name":"IX. The Eternal Fixation"}]},{"chaptername":"Chapter 6 Attack and Fear","topics":[{"id":"TxtChap-6-1","name":"I.Introduction"},{"id":"TxtChap-6-2","name":"II. The Message of the Crucifixion"},{"id":"TxtChap-6-3","name":"III. The Uses of Projection"},{"id":"TxtChap-6-4","name":"IV. The Relinquishment of Attack"},{"id":"TxtChap-6-5a","name":"a. To Have, Give All to All"},{"id":"TxtChap-6-5b","name":"b. To Have Peace, Teach Peace to Learn It"},{"id":"TxtChap-6-5c","name":"c. Be Vigilant Only for God and His Kingdom"}]},{"chaptername":"Chapter 7 The Consistency of the Kingdom","topics":[{"id":"TxtChap-7-1","name":"I.Introduction"},{"id":"TxtChap-7-2","name":"II. Bargaining Versus Healing"},{"id":"TxtChap-7-3","name":"III. The Laws of Mind"},{"id":"TxtChap-7-4","name":"IV. The Unified Curriculum"},{"id":"TxtChap-7-5","name":"V. The Recognition of Truth"},{"id":"TxtChap-7-6","name":"VI. Healing and the Changelessness of Mind"},{"id":"TxtChap-7-7","name":"VII. From Vigilance to Peace"},{"id":"TxtChap-7-8","name":"VIII. The Total Commitment"},{"id":"TxtChap-7-9","name":"IX. The Defense of Conflict"},{"id":"TxtChap-7-10","name":"X. The Extension of the Kingdom"},{"id":"TxtChap-7-11","name":"XI. The Confusion of Strength and Weakness"},{"id":"TxtChap-7-12","name":"XII. The State of Grace"}]},{"chaptername":"Chapter 8 The Journey Back","topics":[{"id":"TxtChap-8-1","name":"I.Introduction"},{"id":"TxtChap-8-2","name":"II. The Direction of the Curriculum"},{"id":"TxtChap-8-3","name":"III. The Rationale For Choice"},{"id":"TxtChap-8-4","name":"IV. The Holy Encounter"},{"id":"TxtChap-8-5","name":"V. The Light of the World"},{"id":"TxtChap-8-6","name":"VI. The Power of Joint Decision"},{"id":"TxtChap-8-7","name":"VII. Communication and the Ego-Body Equation"},{"id":"TxtChap-8-8","name":"VIII. The Body As Means or End"},{"id":"TxtChap-8-9","name":"IX. Healing as Corrected Perception"},{"id":"TxtChap-8-10","name":"X. The Acceptance of Reality"},{"id":"TxtChap-8-11","name":"XI. The Answer to Prayer"}]},{"chaptername":"Chapter 9 The Correction of Error","topics":[{"id":"TxtChap-9-1","name":"I.Introduction"},{"id":"TxtChap-9-2","name":"II. Sanity and Perception"},{"id":"TxtChap-9-3","name":"III. Atonement as a Lesson in Sharing"},{"id":"TxtChap-9-4","name":"IV. The Unhealed Healer"},{"id":"TxtChap-9-5","name":"V. The Awareness of the Holy Spirit"},{"id":"TxtChap-9-6","name":"VI. Salvation and Gods Will"},{"id":"TxtChap-9-7","name":"VII. Grandeur Versus Grandiosity"},{"id":"TxtChap-9-8","name":"VIII. The Inclusiveness of Creation"},{"id":"TxtChap-9-9","name":"IX. The Decision to Forget"},{"id":"TxtChap-9-10","name":"X. Magic Versus Miracles"},{"id":"TxtChap-9-11","name":"XI. The Denial of God"}]},{"chaptername":"Chapter 10 God and the Ego","topics":[{"id":"TxtChap-10-1","name":"I.Introduction"},{"id":"TxtChap-10-2","name":"II. Projection Versus Extension"},{"id":"TxtChap-10-3","name":"III. The Willingness For Healing"},{"id":"TxtChap-10-4","name":"IV. From Darkness to Light"},{"id":"TxtChap-10-5","name":"V. The Inheritance of God's Son"},{"id":"TxtChap-10-6","name":"VI. The Dynamics of the Ego"},{"id":"TxtChap-10-7","name":"VII. Experience and Perception"},{"id":"TxtChap-10-8","name":"VIII. The Problem and the Answer"}]},{"chaptername":"Chapter 11 Gods Plan For Salvation","topics":[{"id":"TxtChap-11-1","name":"I.Introduction"},{"id":"TxtChap-11-2","name":"II. The Judgment of the Holy Spirit"},{"id":"TxtChap-11-3","name":"III. The Mechanism of Miracles"},{"id":"TxtChap-11-4","name":"IV. The Investment in Reality"},{"id":"TxtChap-11-5","name":"V. Seeking and Finding"},{"id":"TxtChap-11-6","name":"VI. The Sane Curriculum"},{"id":"TxtChap-11-7","name":"VII. The Vision of Christ"},{"id":"TxtChap-11-8","name":"VIII. The Guide For Miracles"},{"id":"TxtChap-11-9","name":"IX. Reality and Redemption"},{"id":"TxtChap-11-10","name":"X. Guiltlessness and Invulnerability"}]},{"chaptername":"Chapter 12 The Problem of Guilt","topics":[{"id":"TxtChap-12-1","name":"I.Introduction"},{"id":"TxtChap-12-2","name":"II. Crucifixion By Guilt"},{"id":"TxtChap-12-3","name":"III. The Fear of Redemption"},{"id":"TxtChap-12-4","name":"IV. Healing and Time"},{"id":"TxtChap-12-5","name":"V. The Two Emotions"},{"id":"TxtChap-12-6","name":"VI. Finding the Present"},{"id":"TxtChap-12-7","name":"VII. Attainment of the Real World"}]},{"chaptername":"Chapter 13 From Perception to Knowledge","topics":[{"id":"TxtChap-13-1","name":"I.Introduction"},{"id":"TxtChap-13-2","name":"II. The Role of Healing"},{"id":"TxtChap-13-3","name":"III. The Shadow of Guilt"},{"id":"TxtChap-13-4","name":"IV. Release and Restoration"},{"id":"TxtChap-13-5","name":"V. The Guarantee of Heaven"},{"id":"TxtChap-13-6","name":"VI. The Testimony of Miracles"},{"id":"TxtChap-13-7","name":"VII. The Happy Learner"},{"id":"TxtChap-13-8","name":"VIII. The Decision For Guiltlessness"},{"id":"TxtChap-13-9","name":"IX. The Way of Salvation"}]},{"chaptername":"Chapter 14 Bringing Illusions to Truth","topics":[{"id":"TxtChap-14-1","name":"I.Introduction"},{"id":"TxtChap-14-2","name":"II. Guilt and Guiltlessness"},{"id":"TxtChap-14-3","name":"III. Out of the Darkness"},{"id":"TxtChap-14-4","name":"IV. Perception Without Deceit"},{"id":"TxtChap-14-5","name":"V. The Recognition of Holiness"},{"id":"TxtChap-14-6","name":"VI. The Shift to Miracles"},{"id":"TxtChap-14-7","name":"VII. The Test of Truth"}]},{"chaptername":"Chapter 15 The Purpose of Time","topics":[{"id":"TxtChap-15-1","name":"I.Introduction"},{"id":"TxtChap-15-2","name":"II. Uses of Time"},{"id":"TxtChap-15-3","name":"III. Time and Eternity"},{"id":"TxtChap-15-4","name":"IV. Littleness Versus Magnitude"},{"id":"TxtChap-15-5","name":"V. Practicing the Holy Instant"},{"id":"TxtChap-15-6","name":"VI. The Holy Instant and Special Relationships"},{"id":"TxtChap-15-7","name":"VII. The Holy Instant and the Laws of God"},{"id":"TxtChap-15-8","name":"VIII. The Holy Instant and Communication"},{"id":"TxtChap-15-9","name":"IX. The Holy Instant and Real Relationships"},{"id":"TxtChap-15-10","name":"X. The Time of Christ"},{"id":"TxtChap-15-11","name":"XI. The End of Sacrifice"}]},{"chaptername":"Chapter 16 The Forgiveness of Illusions","topics":[{"id":"TxtChap-16-1","name":"I.Introduction"},{"id":"TxtChap-16-2","name":"II. True Empathy"},{"id":"TxtChap-16-3","name":"III. The Magnitude of Holiness"},{"id":"TxtChap-16-4","name":"IV. The Reward of Teaching"},{"id":"TxtChap-16-5","name":"V. Illusion and Reality of Love"},{"id":"TxtChap-16-6","name":"VI. Specialness and Guilt"},{"id":"TxtChap-16-7","name":"VII. The Bridge to the Real World"},{"id":"TxtChap-16-8","name":"VIII. The End of Illusions"}]},{"chaptername":"Chapter 17 Forgiveness and Healing","topics":[{"id":"TxtChap-17-1","name":"I.Introduction"},{"id":"TxtChap-17-2","name":"II. Fantasy and Distorted Perception"},{"id":"TxtChap-17-3","name":"III. The Forgiven World"},{"id":"TxtChap-17-4","name":"IV. Shadows of the Past"},{"id":"TxtChap-17-4","name":"V. Perception and the Two Worlds"},{"id":"TxtChap-17-5","name":"VI. The Healed Relationship"},{"id":"TxtChap-17-6","name":"VII. Practical Forgiveness"},{"id":"TxtChap-17-7","name":"VIII. The Need for Faith"},{"id":"TxtChap-17-8","name":"IX. The Conditions of Forgiveness"}]},{"chaptername":"Chapter 18 The Dream and the Reality","topics":[{"id":"TxtChap-18-1","name":"I.Introduction"},{"id":"TxtChap-18-2","name":"II. Substitution as a Defense"},{"id":"TxtChap-18-3","name":"III. The Basis of the Dream"},{"id":"TxtChap-18-4","name":"IV. Light in the Dream"},{"id":"TxtChap-18-4","name":"V. The Little Willingness"},{"id":"TxtChap-18-5","name":"VI. The Happy Dream"},{"id":"TxtChap-18-6","name":"VII. Dreams and the Body"},{"id":"TxtChap-18-7","name":"VIII. I Need Do Nothing"},{"id":"TxtChap-18-8","name":"IX. The Purpose of the Body"},{"id":"TxtChap-18-9","name":"X. The Delusional Thought System"},{"id":"TxtChap-18-10","name":"XI. The Passing of the Dream"}]},{"chaptername":"Chapter 19 Beyond the Body","topics":[{"id":"TxtChap-19-1","name":"I.Introduction"},{"id":"TxtChap-19-2","name":"II. Healing and the Mind"},{"id":"TxtChap-19-3","name":"III. Sin Versus Error"},{"id":"TxtChap-19-4","name":"IV. The Unreality of Sin"},{"id":"TxtChap-19-5a","name":"a. The First Obstacle: The Desire to Get Rid of It"},{"id":"TxtChap-19-5b","name":"b. The Second Obstacle: The Belief the Body is Valuable for What it Offers"},{"id":"TTxtChap-19-5c","name":"c. The Third Obstacle: The Attraction of Death"},{"id":"TxtChap-19-5d","name":"d. The Fourth Obstacle: The Fear of God"}]},{"chaptername":"Chapter 20 The Promise of the Resurrection","topics":[{"id":"TxtChap-20-1","name":"I.Introduction"},{"id":"TxtChap-20-2","name":"II. Holy Week"},{"id":"TxtChap-20-3","name":"III. Thorns and Lilies"},{"id":"TxtChap-20-4","name":"IV. Sin as an Adjustment"},{"id":"TxtChap-20-5","name":"V. Entering the Ark"},{"id":"TxtChap-20-6","name":"VI. Heralds of Eternity"},{"id":"TxtChap-20-7","name":"VII. The Temple of the Holy Spirit"},{"id":"TxtChap-20-8","name":"VIII. The Consistency of Means and End"},{"id":"TxtChap-20-9","name":"IX. The Vision of Sinlessness"}]},{"chaptername":"Chapter 21 The Inner Picture","topics":[{"id":"TxtChap-21-1","name":"I.Introduction"},{"id":"TxtChap-21-2","name":"II. The Imagined World"},{"id":"TxtChap-21-3","name":"III. The Responsibility For Sight"},{"id":"TxtChap-21-4","name":"V. The Fear to Look Within"},{"id":"TxtChap-21-5","name":"VI. Reason and Perception"},{"id":"TxtChap-21-6","name":"VII. Reason and Correction"},{"id":"TxtChap-21-7","name":"VIII. Perception and Wishes"},{"id":"TxtChap-21-8","name":"IX. The Inner Shift"}]},{"chaptername":"Chapter 22 Salvation and the Holy Relationship","topics":[{"id":"TxtChap-22-1","name":"I.Introduction"},{"id":"TxtChap-22-2","name":"II. The Message of the Holy Relationship"},{"id":"TxtChap-22-3","name":"III. Your Brother's Sinlessness"},{"id":"TxtChap-22-4","name":"V. The Branching of the Road"},{"id":"TxtChap-22-5","name":"VI. Weakness and Defensiveness"},{"id":"TxtChap-22-6","name":"VII. Freedom and the Holy Spirit"}]},{"chaptername":"Chapter 23 The War Against Yourself","topics":[{"id":"TxtChap-23-1","name":"I.Introduction"},{"id":"TxtChap-23-2","name":"II. The Irreconcilable Beliefs"},{"id":"TxtChap-23-3","name":"III. The Laws of Chaos"},{"id":"TxtChap-23-4","name":"IV. Salvation Without Compromise"},{"id":"TxtChap-23-4","name":"V. The Fear of Life"}]},{"chaptername":"Chapter 24 Specialness and Separation","topics":[{"id":"TxtChap-24-1","name":"I.Introduction"},{"id":"TxtChap-24-2","name":"II. Specialness as a Substitute For Love"},{"id":"TxtChap-24-3","name":"III. The Treachery of Specialness"},{"id":"TxtChap-24-4","name":"IV. The Forgiveness of Specialness"},{"id":"TxtChap-24-5","name":"V. Specialness and Salvation"},{"id":"TxtChap-24-6","name":"VI. The Resolution of the Dream"},{"id":"TxtChap-24-7","name":"VII. Salvation From Fear"},{"id":"TxtChap-24-8","name":"VIII. The Meeting Place"}]},{"chaptername":"Chapter 25 The Remedy","topics":[{"id":"TxtChap-25-1","name":"I.Introduction"},{"id":"TxtChap-25-2","name":"II. The Appointed Task"},{"id":"TxtChap-25-3","name":"III. The Savior From the Dark"},{"id":"TxtChap-25-4","name":"IV. The Fundamental Law of Perception"},{"id":"TxtChap-25-5","name":"V. The Joining of Minds"},{"id":"TxtChap-25-6","name":"VI. The State of Sinlessness"},{"id":"TxtChap-25-7","name":"VII. The Special Function"},{"id":"TxtChap-25-8","name":"VIII. Commuting the Sentence"},{"id":"TxtChap-25-9","name":"IX. The Principle of Salvation"},{"id":"TxtChap-25-10","name":"X. The Justice of Heaven"}]},{"chaptername":"Chapter 26 The Transition","topics":[{"id":"TxtChap-26-1","name":"I.Introduction"},{"id":"TxtChap-26-2","name":"II. The \"Sacrifice\" of Oneness"},{"id":"TxtChap-26-3","name":"III. The Forms of Error"},{"id":"TxtChap-26-4","name":"IV. The Borderland"},{"id":"TxtChap-26-5","name":"V. Where Sin Has Left"},{"id":"TxtChap-26-6","name":"VI. The Little Hindrance"},{"id":"TxtChap-26-7","name":"VII. The Appointed Friend"},{"id":"TxtChap-26-8","name":"VIII. Review of Principles"},{"id":"TxtChap-26-9","name":"IX. The Immediacy of Salvation"},{"id":"TxtChap-26-10","name":"X. For They Have Come"},{"id":"TxtChap-26-11","name":"XI. The Remaining Task"}]},{"chaptername":"Chapter 27 The Body and the Dream","topics":[{"id":"TxtChap-27-1","name":"I.Introduction"},{"id":"TxtChap-27-2","name":"II. The Picture of the Crucifixion"},{"id":"TxtChap-27-3","name":"III. The Fear of Healing"},{"id":"TxtChap-27-4","name":"IV. The Symbol of the Impossible"},{"id":"TxtChap-27-5","name":"V. The Quiet Answer"},{"id":"TxtChap-27-6","name":"VI. The Healing Example"},{"id":"TxtChap-27-7","name":"VII. The Purpose of Pain"},{"id":"TxtChap-27-8","name":"VIII. The Illusion of Suffering"},{"id":"TxtChap-27-9","name":"IX. The \"Hero\" of the Dream"}]},{"chaptername":"Chapter 28 The Undoing of Fear","topics":[{"id":"TxtChap-28-1","name":"I.Introduction"},{"id":"TxtChap-28-2","name":"II. The Present Memory"},{"id":"TxtChap-28-3","name":"III. Reversing Effect and Cause"},{"id":"TxtChap-28-4","name":"IV. The Agreement to Join"},{"id":"TxtChap-28-5","name":"V. The Greater Joining"},{"id":"TxtChap-28-6","name":"VI. The Alternate to Dreams of Fear"},{"id":"TxtChap-28-7","name":"VII. The Secret Vows"},{"id":"TxtChap-28-8","name":"VIII. The Beautiful Relationship"}]},{"chaptername":"Chapter 29 The Awakening","topics":[{"id":"TxtChap-29-1","name":"I.Introduction"},{"id":"TxtChap-29-2","name":"II. The Closing of the Gap"},{"id":"TxtChap-29-3","name":"III. The Coming of the Guest"},{"id":"TxtChap-29-4","name":"IV. God's Witnesses"},{"id":"TxtChap-29-5","name":"V. Dream Roles"},{"id":"TxtChap-29-6","name":"VI. The Changeless Dwelling Place"},{"id":"TxtChap-29-7","name":"VII. Forgiveness and Peace"},{"id":"TxtChap-29-8","name":"VIII. The Lingering Illusion"},{"id":"TxtChap-29-9","name":"IX. Christ and Anti-Christ"},{"id":"TxtChap-29-10","name":"X. The Forgiving Dream"}]},{"chaptername":"Chapter 30 The New Beginning","topics":[{"id":"TxtChap-30-1","name":"I.Introduction"},{"id":"TxtChap-30-2","name":"II. Rules For Decision"},{"id":"TxtChap-30-3","name":"III. Freedom of Will"},{"id":"TxtChap-30-4","name":"IV. Beyond All Idols"},{"id":"TxtChap-30-5","name":"V. The Truth Behind Illusions"},{"id":"TxtChap-30-6","name":"VI. The Only Purpose"},{"id":"TxtChap-30-7","name":"VII. The Justification For Forgiveness"},{"id":"TxtChap-30-8","name":"VIII. The New Interpretation"},{"id":"TxtChap-30-9","name":"IX. Changeless Reality"}]},{"chaptername":"Chapter 31 The Simplicity of Salvation","topics":[{"id":"TxtChap-31-1","name":"I.Introduction"},{"id":"TxtChap-31-2","name":"II. The Illusion of an Enemy"},{"id":"TxtChap-31-3","name":"III. The Self-Accused"},{"id":"TxtChap-31-4","name":"IV. The Real Alternative"},{"id":"TxtChap-31-5","name":"V. Self Concept Versus Self"},{"id":"TxtChap-31-6","name":"VI. Recognizing the Spirit"},{"id":"TxtChap-31-7","name":"VII. The Savior's Vision"},{"id":"TxtChap-31-8","name":"VIII. Choose Once Again"}]}]}
     */

    private String success;
    private String msg;
    private DataBean data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * text : {"text":["Foreword","Introduction"]}
         * chapters : [{"chaptername":"Chapter 1 Introduction to Miracles:","topics":[{"id":"TxtChap-1-1","name":"Principles of Miracles"},{"id":"TxtChap-1-2","name":"II. Distortions of Miracle Impulses"}]},{"chaptername":"Chapter 2:The Illusion of Separation","topics":[{"id":"TxtChap-2-1","name":"I. Introduction"},{"id":"TxtChap-2-2","name":"II.  The Reinterpretation of Defenses"},{"id":"TxtChap-2-3","name":"III.  Healing as Release from Fear"},{"id":"TxtChap-2-4","name":"IV.  Fear as Lack of Love"},{"id":"TxtChap-2-5","name":"V. The Correction for Lack of Love"},{"id":"TxtChap-2-6","name":"VI. The Meaning of the Last Judgment"}]},{"chaptername":"Chapter 3 Retraining the Mind","topics":[{"id":"TxtChap-3-1","name":"I. Introduction"},{"id":"TxtChap-3-2","name":"II. Special Principles For Miracle Workers"},{"id":"TxtChap-3-3","name":"III. Atonement Without Sacrifice"},{"id":"TxtChap-3-4","name":"IV. Miracles as Accurate Perception"},{"id":"TxtChap-3-5","name":"V. Perception Versus Knowledge"},{"id":"TxtChap-3-6","name":"VI. Conflict and the Ego"},{"id":"TxtChap-3-7","name":"VII. The Loss of Certainty"},{"id":"TxtChap-3-8","name":"VIII. Judgment and the Authority Problem"},{"id":"TxtChap-3-9","name":"IX. Creating Versus the Self-Image"}]},{"chaptername":"Chapter 4 The Root of All Evil","topics":[{"id":"TxtChap-4-1","name":"I. Introduction"},{"id":"TxtChap-4-2","name":"II. Right Teaching and Right Learning"},{"id":"TxtChap-4-3","name":"III. The Ego and False Autonomy"},{"id":"TxtChap-4-4","name":"IV. Love Without Conflict"},{"id":"TxtChap-4-5","name":"V. The Escape From Fear"},{"id":"TxtChap-4-6","name":"VI. The Ego-Body Illusion"},{"id":"TxtChap-4-7","name":"VII. The Constant State"},{"id":"TxtChap-4-8","name":"VIII. Creation and Communication"},{"id":"TxtChap-4-9","name":"IX. True Rehabilitation"}]},{"chaptername":"Chapter 5 Healing and Wholeness","topics":[{"id":"TxtChap-5-1","name":"I. Introduction"},{"id":"TxtChap-5-2","name":"II. Healing As Joining"},{"id":"TxtChap-5-3","name":"III. The Mind of the Atonement"},{"id":"TxtChap-5-4","name":"IV. The Voice For God"},{"id":"TxtChap-5-5","name":"V. The Guide to Salvation"},{"id":"TxtChap-5-6","name":"VI. Therapy and Teaching"},{"id":"TxtChap-5-7","name":"VII. The Two Decisions"},{"id":"TxtChap-5-8","name":"VIII. Time and Eternity"},{"id":"TxtChap-5-9","name":"IX. The Eternal Fixation"}]},{"chaptername":"Chapter 6 Attack and Fear","topics":[{"id":"TxtChap-6-1","name":"I.Introduction"},{"id":"TxtChap-6-2","name":"II. The Message of the Crucifixion"},{"id":"TxtChap-6-3","name":"III. The Uses of Projection"},{"id":"TxtChap-6-4","name":"IV. The Relinquishment of Attack"},{"id":"TxtChap-6-5a","name":"a. To Have, Give All to All"},{"id":"TxtChap-6-5b","name":"b. To Have Peace, Teach Peace to Learn It"},{"id":"TxtChap-6-5c","name":"c. Be Vigilant Only for God and His Kingdom"}]},{"chaptername":"Chapter 7 The Consistency of the Kingdom","topics":[{"id":"TxtChap-7-1","name":"I.Introduction"},{"id":"TxtChap-7-2","name":"II. Bargaining Versus Healing"},{"id":"TxtChap-7-3","name":"III. The Laws of Mind"},{"id":"TxtChap-7-4","name":"IV. The Unified Curriculum"},{"id":"TxtChap-7-5","name":"V. The Recognition of Truth"},{"id":"TxtChap-7-6","name":"VI. Healing and the Changelessness of Mind"},{"id":"TxtChap-7-7","name":"VII. From Vigilance to Peace"},{"id":"TxtChap-7-8","name":"VIII. The Total Commitment"},{"id":"TxtChap-7-9","name":"IX. The Defense of Conflict"},{"id":"TxtChap-7-10","name":"X. The Extension of the Kingdom"},{"id":"TxtChap-7-11","name":"XI. The Confusion of Strength and Weakness"},{"id":"TxtChap-7-12","name":"XII. The State of Grace"}]},{"chaptername":"Chapter 8 The Journey Back","topics":[{"id":"TxtChap-8-1","name":"I.Introduction"},{"id":"TxtChap-8-2","name":"II. The Direction of the Curriculum"},{"id":"TxtChap-8-3","name":"III. The Rationale For Choice"},{"id":"TxtChap-8-4","name":"IV. The Holy Encounter"},{"id":"TxtChap-8-5","name":"V. The Light of the World"},{"id":"TxtChap-8-6","name":"VI. The Power of Joint Decision"},{"id":"TxtChap-8-7","name":"VII. Communication and the Ego-Body Equation"},{"id":"TxtChap-8-8","name":"VIII. The Body As Means or End"},{"id":"TxtChap-8-9","name":"IX. Healing as Corrected Perception"},{"id":"TxtChap-8-10","name":"X. The Acceptance of Reality"},{"id":"TxtChap-8-11","name":"XI. The Answer to Prayer"}]},{"chaptername":"Chapter 9 The Correction of Error","topics":[{"id":"TxtChap-9-1","name":"I.Introduction"},{"id":"TxtChap-9-2","name":"II. Sanity and Perception"},{"id":"TxtChap-9-3","name":"III. Atonement as a Lesson in Sharing"},{"id":"TxtChap-9-4","name":"IV. The Unhealed Healer"},{"id":"TxtChap-9-5","name":"V. The Awareness of the Holy Spirit"},{"id":"TxtChap-9-6","name":"VI. Salvation and Gods Will"},{"id":"TxtChap-9-7","name":"VII. Grandeur Versus Grandiosity"},{"id":"TxtChap-9-8","name":"VIII. The Inclusiveness of Creation"},{"id":"TxtChap-9-9","name":"IX. The Decision to Forget"},{"id":"TxtChap-9-10","name":"X. Magic Versus Miracles"},{"id":"TxtChap-9-11","name":"XI. The Denial of God"}]},{"chaptername":"Chapter 10 God and the Ego","topics":[{"id":"TxtChap-10-1","name":"I.Introduction"},{"id":"TxtChap-10-2","name":"II. Projection Versus Extension"},{"id":"TxtChap-10-3","name":"III. The Willingness For Healing"},{"id":"TxtChap-10-4","name":"IV. From Darkness to Light"},{"id":"TxtChap-10-5","name":"V. The Inheritance of God's Son"},{"id":"TxtChap-10-6","name":"VI. The Dynamics of the Ego"},{"id":"TxtChap-10-7","name":"VII. Experience and Perception"},{"id":"TxtChap-10-8","name":"VIII. The Problem and the Answer"}]},{"chaptername":"Chapter 11 Gods Plan For Salvation","topics":[{"id":"TxtChap-11-1","name":"I.Introduction"},{"id":"TxtChap-11-2","name":"II. The Judgment of the Holy Spirit"},{"id":"TxtChap-11-3","name":"III. The Mechanism of Miracles"},{"id":"TxtChap-11-4","name":"IV. The Investment in Reality"},{"id":"TxtChap-11-5","name":"V. Seeking and Finding"},{"id":"TxtChap-11-6","name":"VI. The Sane Curriculum"},{"id":"TxtChap-11-7","name":"VII. The Vision of Christ"},{"id":"TxtChap-11-8","name":"VIII. The Guide For Miracles"},{"id":"TxtChap-11-9","name":"IX. Reality and Redemption"},{"id":"TxtChap-11-10","name":"X. Guiltlessness and Invulnerability"}]},{"chaptername":"Chapter 12 The Problem of Guilt","topics":[{"id":"TxtChap-12-1","name":"I.Introduction"},{"id":"TxtChap-12-2","name":"II. Crucifixion By Guilt"},{"id":"TxtChap-12-3","name":"III. The Fear of Redemption"},{"id":"TxtChap-12-4","name":"IV. Healing and Time"},{"id":"TxtChap-12-5","name":"V. The Two Emotions"},{"id":"TxtChap-12-6","name":"VI. Finding the Present"},{"id":"TxtChap-12-7","name":"VII. Attainment of the Real World"}]},{"chaptername":"Chapter 13 From Perception to Knowledge","topics":[{"id":"TxtChap-13-1","name":"I.Introduction"},{"id":"TxtChap-13-2","name":"II. The Role of Healing"},{"id":"TxtChap-13-3","name":"III. The Shadow of Guilt"},{"id":"TxtChap-13-4","name":"IV. Release and Restoration"},{"id":"TxtChap-13-5","name":"V. The Guarantee of Heaven"},{"id":"TxtChap-13-6","name":"VI. The Testimony of Miracles"},{"id":"TxtChap-13-7","name":"VII. The Happy Learner"},{"id":"TxtChap-13-8","name":"VIII. The Decision For Guiltlessness"},{"id":"TxtChap-13-9","name":"IX. The Way of Salvation"}]},{"chaptername":"Chapter 14 Bringing Illusions to Truth","topics":[{"id":"TxtChap-14-1","name":"I.Introduction"},{"id":"TxtChap-14-2","name":"II. Guilt and Guiltlessness"},{"id":"TxtChap-14-3","name":"III. Out of the Darkness"},{"id":"TxtChap-14-4","name":"IV. Perception Without Deceit"},{"id":"TxtChap-14-5","name":"V. The Recognition of Holiness"},{"id":"TxtChap-14-6","name":"VI. The Shift to Miracles"},{"id":"TxtChap-14-7","name":"VII. The Test of Truth"}]},{"chaptername":"Chapter 15 The Purpose of Time","topics":[{"id":"TxtChap-15-1","name":"I.Introduction"},{"id":"TxtChap-15-2","name":"II. Uses of Time"},{"id":"TxtChap-15-3","name":"III. Time and Eternity"},{"id":"TxtChap-15-4","name":"IV. Littleness Versus Magnitude"},{"id":"TxtChap-15-5","name":"V. Practicing the Holy Instant"},{"id":"TxtChap-15-6","name":"VI. The Holy Instant and Special Relationships"},{"id":"TxtChap-15-7","name":"VII. The Holy Instant and the Laws of God"},{"id":"TxtChap-15-8","name":"VIII. The Holy Instant and Communication"},{"id":"TxtChap-15-9","name":"IX. The Holy Instant and Real Relationships"},{"id":"TxtChap-15-10","name":"X. The Time of Christ"},{"id":"TxtChap-15-11","name":"XI. The End of Sacrifice"}]},{"chaptername":"Chapter 16 The Forgiveness of Illusions","topics":[{"id":"TxtChap-16-1","name":"I.Introduction"},{"id":"TxtChap-16-2","name":"II. True Empathy"},{"id":"TxtChap-16-3","name":"III. The Magnitude of Holiness"},{"id":"TxtChap-16-4","name":"IV. The Reward of Teaching"},{"id":"TxtChap-16-5","name":"V. Illusion and Reality of Love"},{"id":"TxtChap-16-6","name":"VI. Specialness and Guilt"},{"id":"TxtChap-16-7","name":"VII. The Bridge to the Real World"},{"id":"TxtChap-16-8","name":"VIII. The End of Illusions"}]},{"chaptername":"Chapter 17 Forgiveness and Healing","topics":[{"id":"TxtChap-17-1","name":"I.Introduction"},{"id":"TxtChap-17-2","name":"II. Fantasy and Distorted Perception"},{"id":"TxtChap-17-3","name":"III. The Forgiven World"},{"id":"TxtChap-17-4","name":"IV. Shadows of the Past"},{"id":"TxtChap-17-4","name":"V. Perception and the Two Worlds"},{"id":"TxtChap-17-5","name":"VI. The Healed Relationship"},{"id":"TxtChap-17-6","name":"VII. Practical Forgiveness"},{"id":"TxtChap-17-7","name":"VIII. The Need for Faith"},{"id":"TxtChap-17-8","name":"IX. The Conditions of Forgiveness"}]},{"chaptername":"Chapter 18 The Dream and the Reality","topics":[{"id":"TxtChap-18-1","name":"I.Introduction"},{"id":"TxtChap-18-2","name":"II. Substitution as a Defense"},{"id":"TxtChap-18-3","name":"III. The Basis of the Dream"},{"id":"TxtChap-18-4","name":"IV. Light in the Dream"},{"id":"TxtChap-18-4","name":"V. The Little Willingness"},{"id":"TxtChap-18-5","name":"VI. The Happy Dream"},{"id":"TxtChap-18-6","name":"VII. Dreams and the Body"},{"id":"TxtChap-18-7","name":"VIII. I Need Do Nothing"},{"id":"TxtChap-18-8","name":"IX. The Purpose of the Body"},{"id":"TxtChap-18-9","name":"X. The Delusional Thought System"},{"id":"TxtChap-18-10","name":"XI. The Passing of the Dream"}]},{"chaptername":"Chapter 19 Beyond the Body","topics":[{"id":"TxtChap-19-1","name":"I.Introduction"},{"id":"TxtChap-19-2","name":"II. Healing and the Mind"},{"id":"TxtChap-19-3","name":"III. Sin Versus Error"},{"id":"TxtChap-19-4","name":"IV. The Unreality of Sin"},{"id":"TxtChap-19-5a","name":"a. The First Obstacle: The Desire to Get Rid of It"},{"id":"TxtChap-19-5b","name":"b. The Second Obstacle: The Belief the Body is Valuable for What it Offers"},{"id":"TTxtChap-19-5c","name":"c. The Third Obstacle: The Attraction of Death"},{"id":"TxtChap-19-5d","name":"d. The Fourth Obstacle: The Fear of God"}]},{"chaptername":"Chapter 20 The Promise of the Resurrection","topics":[{"id":"TxtChap-20-1","name":"I.Introduction"},{"id":"TxtChap-20-2","name":"II. Holy Week"},{"id":"TxtChap-20-3","name":"III. Thorns and Lilies"},{"id":"TxtChap-20-4","name":"IV. Sin as an Adjustment"},{"id":"TxtChap-20-5","name":"V. Entering the Ark"},{"id":"TxtChap-20-6","name":"VI. Heralds of Eternity"},{"id":"TxtChap-20-7","name":"VII. The Temple of the Holy Spirit"},{"id":"TxtChap-20-8","name":"VIII. The Consistency of Means and End"},{"id":"TxtChap-20-9","name":"IX. The Vision of Sinlessness"}]},{"chaptername":"Chapter 21 The Inner Picture","topics":[{"id":"TxtChap-21-1","name":"I.Introduction"},{"id":"TxtChap-21-2","name":"II. The Imagined World"},{"id":"TxtChap-21-3","name":"III. The Responsibility For Sight"},{"id":"TxtChap-21-4","name":"V. The Fear to Look Within"},{"id":"TxtChap-21-5","name":"VI. Reason and Perception"},{"id":"TxtChap-21-6","name":"VII. Reason and Correction"},{"id":"TxtChap-21-7","name":"VIII. Perception and Wishes"},{"id":"TxtChap-21-8","name":"IX. The Inner Shift"}]},{"chaptername":"Chapter 22 Salvation and the Holy Relationship","topics":[{"id":"TxtChap-22-1","name":"I.Introduction"},{"id":"TxtChap-22-2","name":"II. The Message of the Holy Relationship"},{"id":"TxtChap-22-3","name":"III. Your Brother's Sinlessness"},{"id":"TxtChap-22-4","name":"V. The Branching of the Road"},{"id":"TxtChap-22-5","name":"VI. Weakness and Defensiveness"},{"id":"TxtChap-22-6","name":"VII. Freedom and the Holy Spirit"}]},{"chaptername":"Chapter 23 The War Against Yourself","topics":[{"id":"TxtChap-23-1","name":"I.Introduction"},{"id":"TxtChap-23-2","name":"II. The Irreconcilable Beliefs"},{"id":"TxtChap-23-3","name":"III. The Laws of Chaos"},{"id":"TxtChap-23-4","name":"IV. Salvation Without Compromise"},{"id":"TxtChap-23-4","name":"V. The Fear of Life"}]},{"chaptername":"Chapter 24 Specialness and Separation","topics":[{"id":"TxtChap-24-1","name":"I.Introduction"},{"id":"TxtChap-24-2","name":"II. Specialness as a Substitute For Love"},{"id":"TxtChap-24-3","name":"III. The Treachery of Specialness"},{"id":"TxtChap-24-4","name":"IV. The Forgiveness of Specialness"},{"id":"TxtChap-24-5","name":"V. Specialness and Salvation"},{"id":"TxtChap-24-6","name":"VI. The Resolution of the Dream"},{"id":"TxtChap-24-7","name":"VII. Salvation From Fear"},{"id":"TxtChap-24-8","name":"VIII. The Meeting Place"}]},{"chaptername":"Chapter 25 The Remedy","topics":[{"id":"TxtChap-25-1","name":"I.Introduction"},{"id":"TxtChap-25-2","name":"II. The Appointed Task"},{"id":"TxtChap-25-3","name":"III. The Savior From the Dark"},{"id":"TxtChap-25-4","name":"IV. The Fundamental Law of Perception"},{"id":"TxtChap-25-5","name":"V. The Joining of Minds"},{"id":"TxtChap-25-6","name":"VI. The State of Sinlessness"},{"id":"TxtChap-25-7","name":"VII. The Special Function"},{"id":"TxtChap-25-8","name":"VIII. Commuting the Sentence"},{"id":"TxtChap-25-9","name":"IX. The Principle of Salvation"},{"id":"TxtChap-25-10","name":"X. The Justice of Heaven"}]},{"chaptername":"Chapter 26 The Transition","topics":[{"id":"TxtChap-26-1","name":"I.Introduction"},{"id":"TxtChap-26-2","name":"II. The \"Sacrifice\" of Oneness"},{"id":"TxtChap-26-3","name":"III. The Forms of Error"},{"id":"TxtChap-26-4","name":"IV. The Borderland"},{"id":"TxtChap-26-5","name":"V. Where Sin Has Left"},{"id":"TxtChap-26-6","name":"VI. The Little Hindrance"},{"id":"TxtChap-26-7","name":"VII. The Appointed Friend"},{"id":"TxtChap-26-8","name":"VIII. Review of Principles"},{"id":"TxtChap-26-9","name":"IX. The Immediacy of Salvation"},{"id":"TxtChap-26-10","name":"X. For They Have Come"},{"id":"TxtChap-26-11","name":"XI. The Remaining Task"}]},{"chaptername":"Chapter 27 The Body and the Dream","topics":[{"id":"TxtChap-27-1","name":"I.Introduction"},{"id":"TxtChap-27-2","name":"II. The Picture of the Crucifixion"},{"id":"TxtChap-27-3","name":"III. The Fear of Healing"},{"id":"TxtChap-27-4","name":"IV. The Symbol of the Impossible"},{"id":"TxtChap-27-5","name":"V. The Quiet Answer"},{"id":"TxtChap-27-6","name":"VI. The Healing Example"},{"id":"TxtChap-27-7","name":"VII. The Purpose of Pain"},{"id":"TxtChap-27-8","name":"VIII. The Illusion of Suffering"},{"id":"TxtChap-27-9","name":"IX. The \"Hero\" of the Dream"}]},{"chaptername":"Chapter 28 The Undoing of Fear","topics":[{"id":"TxtChap-28-1","name":"I.Introduction"},{"id":"TxtChap-28-2","name":"II. The Present Memory"},{"id":"TxtChap-28-3","name":"III. Reversing Effect and Cause"},{"id":"TxtChap-28-4","name":"IV. The Agreement to Join"},{"id":"TxtChap-28-5","name":"V. The Greater Joining"},{"id":"TxtChap-28-6","name":"VI. The Alternate to Dreams of Fear"},{"id":"TxtChap-28-7","name":"VII. The Secret Vows"},{"id":"TxtChap-28-8","name":"VIII. The Beautiful Relationship"}]},{"chaptername":"Chapter 29 The Awakening","topics":[{"id":"TxtChap-29-1","name":"I.Introduction"},{"id":"TxtChap-29-2","name":"II. The Closing of the Gap"},{"id":"TxtChap-29-3","name":"III. The Coming of the Guest"},{"id":"TxtChap-29-4","name":"IV. God's Witnesses"},{"id":"TxtChap-29-5","name":"V. Dream Roles"},{"id":"TxtChap-29-6","name":"VI. The Changeless Dwelling Place"},{"id":"TxtChap-29-7","name":"VII. Forgiveness and Peace"},{"id":"TxtChap-29-8","name":"VIII. The Lingering Illusion"},{"id":"TxtChap-29-9","name":"IX. Christ and Anti-Christ"},{"id":"TxtChap-29-10","name":"X. The Forgiving Dream"}]},{"chaptername":"Chapter 30 The New Beginning","topics":[{"id":"TxtChap-30-1","name":"I.Introduction"},{"id":"TxtChap-30-2","name":"II. Rules For Decision"},{"id":"TxtChap-30-3","name":"III. Freedom of Will"},{"id":"TxtChap-30-4","name":"IV. Beyond All Idols"},{"id":"TxtChap-30-5","name":"V. The Truth Behind Illusions"},{"id":"TxtChap-30-6","name":"VI. The Only Purpose"},{"id":"TxtChap-30-7","name":"VII. The Justification For Forgiveness"},{"id":"TxtChap-30-8","name":"VIII. The New Interpretation"},{"id":"TxtChap-30-9","name":"IX. Changeless Reality"}]},{"chaptername":"Chapter 31 The Simplicity of Salvation","topics":[{"id":"TxtChap-31-1","name":"I.Introduction"},{"id":"TxtChap-31-2","name":"II. The Illusion of an Enemy"},{"id":"TxtChap-31-3","name":"III. The Self-Accused"},{"id":"TxtChap-31-4","name":"IV. The Real Alternative"},{"id":"TxtChap-31-5","name":"V. Self Concept Versus Self"},{"id":"TxtChap-31-6","name":"VI. Recognizing the Spirit"},{"id":"TxtChap-31-7","name":"VII. The Savior's Vision"},{"id":"TxtChap-31-8","name":"VIII. Choose Once Again"}]}]
         */

        private TextBean text;
        private List<ChaptersBean> chapters;

        public TextBean getText() {
            return text;
        }

        public void setText(TextBean text) {
            this.text = text;
        }

        public List<ChaptersBean> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBean> chapters) {
            this.chapters = chapters;
        }

        public static class TextBean {
            private List<String> text;

            public List<String> getText() {
                return text;
            }

            public void setText(List<String> text) {
                this.text = text;
            }
        }

        public static class ChaptersBean {
            /**
             * chaptername : Chapter 1 Introduction to Miracles:
             * topics : [{"id":"TxtChap-1-1","name":"Principles of Miracles"},{"id":"TxtChap-1-2","name":"II. Distortions of Miracle Impulses"}]
             */

            private String chaptername;
            private List<TopicsBean> topics;
            private Boolean isBookmarked = false;

            public String getChaptername() {
                return chaptername;
            }

            public void setChaptername(String chaptername) {
                this.chaptername = chaptername;
            }

            public Boolean getBookmarked() {
                return isBookmarked;
            }

            public void setBookmarked(Boolean bookmarked) {
                isBookmarked = bookmarked;
            }

            public List<TopicsBean> getTopics() {
                return topics;
            }

            public void setTopics(List<TopicsBean> topics) {
                this.topics = topics;
            }

            public static class TopicsBean implements Parcelable {
                public static final Creator<TopicsBean> CREATOR = new Creator<TopicsBean>() {
                    @Override
                    public TopicsBean createFromParcel(Parcel in) {
                        return new TopicsBean(in);
                    }

                    @Override
                    public TopicsBean[] newArray(int size) {
                        return new TopicsBean[size];
                    }
                };
                /**
                 * id : TxtChap-1-1
                 * name : Principles of Miracles
                 */

                private String id;
                private String name;
                private int isBookMarked = 1;

                protected TopicsBean(Parcel in) {
                    id = in.readString();
                    name = in.readString();
                    isBookMarked = in.readInt();
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getBookMarked() {
                    return isBookMarked;
                }

                public void setBookMarked(int bookMarked) {
                    isBookMarked = bookMarked;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(id);
                    dest.writeString(name);
                    dest.writeInt(isBookMarked );
                }
            }
        }
    }
}