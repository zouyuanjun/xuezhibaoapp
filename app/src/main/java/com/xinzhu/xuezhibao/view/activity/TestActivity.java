package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {
    List<String> datalist = new ArrayList<>();
    List<String> anslist = new ArrayList<>();

    HashMap<Integer, Integer> ansmap = new HashMap<>();
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ans1)
    RadioButton ans1;
    @BindView(R.id.ans2)
    RadioButton ans2;
    @BindView(R.id.ans3)
    RadioButton ans3;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    Integer crrentindex = 0;
    boolean issyschangecheck = false;
    boolean iscommit = false;
    double yuedu = 0;
    double shumian = 0;
    double zhuyili = 0;
    double duodong = 0;
    double xuexi = 0;
    double qingxu = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 1) {
                Log.d("收到消息" + crrentindex);
                tvTitle.setText(datalist.get(crrentindex));
                radiogroup.setClickable(true);
                ans3.setClickable(true);
                ans2.setClickable(true);
                ans1.setClickable(true);
                issyschangecheck = true;
                radiogroup.clearCheck();

            }
        }
    };
    @BindView(R.id.last)
    ShapeCornerBgView last;
    Context context;
    String result = "分数说明\n" +
            "1-3分为轻度，不会产生太大影响，关注；4-6分为中度，需要家长做出积极的干预；7-9分为重度，需要重视，帮助和训练孩子度过困难。\n您的测评结果";
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.textView8)
    TextView textView8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        context = this;
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.ans1:
                        if (issyschangecheck) {
                            issyschangecheck = false;
                            return;
                        }
                        radioGroup.setClickable(false);
                        ans1.setClickable(false);
                        Log.d("选择第一个");
                        ansmap.put(crrentindex, 2);
                        ans3.setClickable(false);
                        ans2.setClickable(false);
                        if (crrentindex < 49) {
                            crrentindex++;
                            handler.sendMessageDelayed(handler.obtainMessage(1), 1000);
                        } else {
                            textView8.setText("提示：您已答完所有题目，确认答案后请点击右上角提交");
                        }

                        break;
                    case R.id.ans2:
                        if (issyschangecheck) {
                            issyschangecheck = false;
                            return;
                        }
                        Log.d("选择第2个");
                        radioGroup.setClickable(false);
                        ans2.setClickable(false);
                        ans3.setClickable(false);
                        ans1.setClickable(false);
                        ansmap.put(crrentindex, 1);
                        if (crrentindex < 49) {
                            crrentindex++;
                            handler.sendMessageDelayed(handler.obtainMessage(1), 100);
                        } else {
                            textView8.setText("提示：您已答完所有题目，确认答案后请点击右上角提交");
                        }

                        break;
                    case R.id.ans3:
                        if (issyschangecheck) {
                            issyschangecheck = false;
                            return;
                        }
                        Log.d("选择第3个");
                        radioGroup.setClickable(false);
                        ans3.setClickable(false);
                        ans2.setClickable(false);
                        ans1.setClickable(false);
                        ansmap.put(crrentindex, 0);
                        if (crrentindex < 49) {
                            crrentindex++;
                            handler.sendMessageDelayed(handler.obtainMessage(1), 1000);
                        } else {
                            textView8.setText("提示：您已答完所有题目，确认答案后请点击右上角提交");
                        }

                        break;
                }

            }
        });
        initdata();
        tvTitle.setText(datalist.get(0));
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crrentindex != 49) {
                    BToast.error(context).text("必须全部答完才可以提交分数哦").show();
                    return;
                }
                if (iscommit) {
                    BToast.error(context).text("不用重复提交哦").show();
                    return;
                }
                iscommit = true;
                appbar.setRightTextVisible(false);
                for (Integer key : ansmap.keySet()) {
                    if (key <= 9 && key >= 0) {
                        yuedu = yuedu + ansmap.get(key);
                    } else if (key > 9 && key <= 18) {
                        shumian = shumian + ansmap.get(key);
                    } else if (key > 18 && key <= 27) {
                        zhuyili = zhuyili + ansmap.get(key);
                    } else if (key > 27 && key <= 36) {
                        duodong = duodong + ansmap.get(key);
                    } else if (key > 36 && key <= 45) {
                        xuexi = xuexi + ansmap.get(key);
                    } else if (key > 46 && key <= 50) {
                        qingxu = qingxu + ansmap.get(key);
                    }
                    Log.d("题目是" + key + ",答案是：" + ansmap.get(key));
                }
                yuedu = 5 + 2 * (yuedu - 6.713) / 3.167;
                shumian = 5 + 2 * (shumian - 6.713) / 3.167;
                zhuyili = 5 + 2 * (zhuyili - 6.713) / 3.167;
                duodong = 5 + 2 * (duodong - 6.713) / 3.167;
                xuexi = 5 + 2 * (xuexi - 6.713) / 3.167;
                qingxu = 5 + 2 * (qingxu - 3.727) / 1.753;
                if (yuedu > 9) {
                    yuedu = 9;
                }
                if (shumian > 9) {
                    shumian = 9;
                }
                if (zhuyili > 9) {
                    zhuyili = 9;
                }
                if (duodong > 9) {
                    duodong = 9;
                }
                if (xuexi ==0) {
                    xuexi = 1;
                }
                if (qingxu ==0) {
                    qingxu = 1;
                }
                if (shumian ==0) {
                    shumian = 1;
                }
                if (zhuyili ==0) {
                    zhuyili = 1;
                }
                if (duodong ==0) {
                    duodong = 1;
                }
                if (xuexi ==0) {
                    xuexi = 1;
                }
                if (qingxu ==0) {
                    qingxu =1;
                }
                if (yuedu <= 3) {
                    result = result + "\n\n阅读理解：" + (int)yuedu + "分\n" + anslist.get(0);
                }
                if (yuedu > 3 && yuedu < 7) {
                    result = result + "\n\n阅读理解：" + (int)yuedu + "分\n" + anslist.get(1);
                }
                if (yuedu > 7 && yuedu < 14) {
                    result = result + "\n\n阅读理解：" + (int)yuedu + "分\n" + anslist.get(2);
                }
                if (shumian <= 3) {
                    result = result + "\n\n书面表达与写作：" +(int) shumian + "分\n" + anslist.get(3);
                }
                if (shumian > 3 && shumian < 7) {
                    result = result + "\n\n书面表达与写作：" +(int) shumian + "分\n" + anslist.get(4);
                }
                if (shumian > 7 && shumian < 14) {
                    result = result + "\n\n书面表达与写作：" + (int)shumian + "分\n" + anslist.get(5);
                }
                if (zhuyili <= 3) {
                    result = result + "\n\n注意力：" + (int)zhuyili + "分\n" + anslist.get(6);
                }
                if (zhuyili > 3 && zhuyili < 7) {
                    result = result + "\n\n注意力：" + (int)zhuyili + "分\n" + anslist.get(7);
                }
                if (zhuyili > 7 && zhuyili < 14) {
                    result = result + "\n\n注意力：" + (int)zhuyili + "分\n" + anslist.get(8);
                }
                if (duodong <= 3) {
                    result = result + "\n\n多动与抑制：" + (int)duodong + "分\n" + anslist.get(9);
                }
                if (duodong > 3 && duodong < 7) {
                    result = result + "\n\n多动与抑制：" + (int)duodong + "分\n" + anslist.get(10);
                }
                if (duodong > 7 && duodong < 14) {
                    result = result + "\n\n多动与抑制：" + (int)duodong + "分\n" + anslist.get(11);
                }
                if (xuexi <= 3) {
                    result = result + "\n\n学习和感知：" + (int)xuexi + "分\n" + anslist.get(12);
                }
                if (xuexi > 3 && xuexi < 7) {
                    result = result + "\n\n学习和感知：" + (int)xuexi + "分\n" + anslist.get(13);
                }
                if (xuexi > 7 && xuexi < 14) {
                    result = result + "\n\n学习和感知：" + (int)xuexi + "分\n" + anslist.get(14);
                }
                if (qingxu <= 3) {
                    result = result + "\n\n情绪：" + (int)qingxu + "分\n" + anslist.get(15);
                }
                if (qingxu > 3 && qingxu < 7) {
                    result = result + "\n\n情绪：" + (int)qingxu + "分\n" + anslist.get(16);
                }
                if (qingxu > 7 && qingxu < 14) {
                    result = result + "\n\n情绪：" + (int)qingxu + "分\n" + anslist.get(17);
                }
                tvResult.setText(result);
                nestedScrollView.setVisibility(View.VISIBLE);
                textView8.setVisibility(View.GONE);

            }
        });
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crrentindex != 49) {
                    CustomDialog.Builder builder = new CustomDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("您还没有答完题目，退出会清空答案，确定要退出吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return;
                }
                if (!iscommit) {
                    CustomDialog.Builder builder = new CustomDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("您还没有查看测评结果，退出会清空答案，确定要退出吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return;
                }
                finish();
            }
        });
    }


    void initdata() {
        String s1 = "1.阅读速度明显慢于同年龄正常水平。";
        String s2 = "2.书面理解能力比口头理解能力差（听比读好）。 ";
        String s3 = "3.很多词语在说话时懂得运用，但却写不出来，书写能力比口语表达差很多（说比写好）。";
        String s4 = "4.阅读理解能力较同龄的学生差很多，难以理解或找不到重点。";
        String s5 = "5.朗读时不能流畅地读出一段话，偶尔读错字。";
        String s6 = "6.朗读时因不懂读字而常常停顿（非生字）。";
        String s7 = "7.朗读时混淆读音相近的字词，例如[波子]及[多士]。";
        String s8 = "8.朗读时音欢摇头晃脑，或者需要手指或其它工具跟随铺助。";
        String s9 = "9.朗读时有漏字或跳行的情况，或者会把词语前后颠倒。";
        String s10 = "10.书写、抄写速度慢，需要比别人更长时间来完成抄写作业。";
        String s11 = "11.书写时常犯错，例如漏写笔画，字体的结构不匀称，潦草，感觉吃力。";
        String s12 = "12.默写常常不及格，写错字或是字序顺倒或写反字。";
        String s13 = "13.抄写黑板时会漏字，或需要看一笔写一笔，或者只能看一个偏旁。";
        String s14 = "14.同一个字在同一篇文章有多种不同的写法，有的错误，有的正确。";
        String s15 = "15.作文、作句子只能勉强达意，文法上有错误。";
        String s16 = "16.会漏字，例如[这很玩]漏了“好”字；或写多余的字，例如[这很非常好]多了“很”字。";
        String s17 = "17.次序混乱，例如[今天上学不用]，把“上学”和“不用”顺序颠倒。";
        String s18 = "18.常写出界，对不齐作业本的行或格等。";
        String s19 = "19.经常不注意细节，在学校课业、日常生活及其它活动总经常犯粗心大意的错";
        String s20 = "20.注意力无法长时间集中于课业或游戏上（非电子游戏）。";
        String s21 = "21.别人和他（她)说话，他（她）经常没有注意听。";
        String s22 = "22.常常无法完成他人的指令，且无法完成学校课业，其它事情或任务";
        String s23 = "23.对于组织活动或任务经常感到困难";
        String s24 = "24.经常逃避、厌恶或不甘心地从事较花心思的任务";
        String s25 = "25.经常遗失所需之物（如玩具、书本、作业本、铅笔或工具）";
        String s26 = "26.较容易被外界刺激转移注意力";
        String s27 = "27.经常在日常生活中丢三落四";
        String s28 = "28.手、脚常常不安地动来动去或坐不住";
        String s29 = "29.常常在课堂上或其他应该坐好的地方站起来";
        String s30 = "30.经常在不适当的场合跑来跑去或爬来爬去（这在青少年或成人身上可能是个体受限制的不安感受）";
        String s31 = "31.常常很难静下来玩，或安静地从事活动";
        String s32 = "32.经常处于活动状态，不安静";
        String s33 = "33.常常话太多";
        String s34 = "34.常常在问题尚未说完前便抢先回答";
        String s35 = "35.从事的活动需轮流时，常不耐烦等待";
        String s36 = "36.常常中断及干扰别人，例如介入他人的谈话或游戏";
        String s37 = "37.分辨上下左右有困难或穿反拖鞋";
        String s38 = "38.方向感较差，需要较长时间才懂得前往校内或小区的特别教室，如音乐室，电脑室，管理处等";
        String s39 = "39.倒转字母或词，如b写成d，3写成E";
        String s40 = "40.体育表现差，跳绳、接球、抛球有困难";
        String s41 = "41.音律感差，上音乐课时不能依一些简单的拍子打节拍";
        String s42 = "42.与同龄学生比较，系鞋带，扣纽扣有困难或笨拙";
        String s43 = "43.记人名、地名感到困难，或者常遗失东西或忘记事情";
        String s44 = "44.记一些朗朗上口的儿歌或短诗也感到困难";
        String s45 = "45.记不得有关自己的资料，如电话号码，家庭地址等";
        String s46 = "46.生气时不能控制自己的情绪，用合适方式表达（如语言），容易发生过激行为（如哭闹不停、摔东西、打人、歇斯底里）";
        String s47 = "47.每到新地方容易紧张，要过一会才能安静下来（10分钟或更长）";
        String s48 = "48.遇到挫折时会产生攻击行为";
        String s49 = "49.看上去紧张、不安或害怕、退缩";
        String s50 = "50.感到压力时容易发脾气、攻击人或说他头痛、胃痛等";

        datalist.add(s1);
        datalist.add(s2);
        datalist.add(s3);
        datalist.add(s4);
        datalist.add(s5);
        datalist.add(s6);
        datalist.add(s7);
        datalist.add(s8);
        datalist.add(s9);
        datalist.add(s10);
        datalist.add(s11);
        datalist.add(s12);
        datalist.add(s13);
        datalist.add(s14);
        datalist.add(s15);
        datalist.add(s16);
        datalist.add(s17);
        datalist.add(s18);
        datalist.add(s19);
        datalist.add(s20);
        datalist.add(s21);
        datalist.add(s22);
        datalist.add(s23);
        datalist.add(s24);
        datalist.add(s25);
        datalist.add(s26);
        datalist.add(s27);
        datalist.add(s28);
        datalist.add(s29);
        datalist.add(s30);
        datalist.add(s31);
        datalist.add(s32);
        datalist.add(s33);
        datalist.add(s34);
        datalist.add(s35);
        datalist.add(s36);
        datalist.add(s37);
        datalist.add(s38);
        datalist.add(s39);
        datalist.add(s40);
        datalist.add(s41);
        datalist.add(s42);
        datalist.add(s43);
        datalist.add(s44);
        datalist.add(s45);
        datalist.add(s46);
        datalist.add(s47);
        datalist.add(s48);
        datalist.add(s49);
        datalist.add(s50);

        anslist.add("1-3分：阅读与理解方面没有太大问题，朗读比较流畅，对文字的解码能力较好，能准确的理解文字的意思，抓住重点，口头表达能力也比较强，有较好的言语组织能力和口语表达能力。");
        anslist.add("4-6分：阅读与理解能力受中等程度的影响，阅读和理解文章时需要较高注意力来维持，一旦注意力不集中，则阅读的成绩也相应比较差，偶有卡顿，不是很流畅，阅读速度受到影响，会有漏字、回读等现象，对于文章的大体意思能够理解，但会抓重点不是很准确。");
        anslist.add("7-9分：阅读存在困难，文字的解码能力不足，存在发展性阅读障碍，影响文字阅读及理解、记忆运作、提取生字能力及信息处理速度，语音处理、视觉感知、听觉认知、专注力、分辨左右、列序或组织等能力也明显较同龄儿童弱。阅读会出现加字、漏字、用别的字（词）替代，跳字、跳行，重复阅读等现象，朗读不流畅，常常因为不能懂读字而产生停顿，或者念破词句，无法理解文字内容，混淆相似字，喜欢看图画类书籍而排斥文字多的书籍，断句难，不能理解应用题，阅读缓慢。常常伴随记忆力不好，不容易依靠自己理解内容，抓不住重点，但与孩子的智商无关。语言组织能力和表达能力比较差，常常不能准确的表达自己的意思，在人际交往时，常常会以动作来表达自己的需求和感情，经常被误会为不够友好，喜欢招惹别人，甚至也会表现为自信心不强，自卑，不与人打交道。");
        anslist.add("1-3分：书面表达与写作较好，文字组织能力较流畅，表达有条理。");
        anslist.add("4-6分：书面表达能力弱，能将意思表达出来，但有时条理欠缺，容易出现赘述，反复的涂改。写字显慢，书写时有时会因为粗心犯错，作文写句词能达意，但会有文法错误，积极的训练能较好的改善这种情况。");
        anslist.add("7-9分：书面表达能力很差，书写、拼字、写作及数理上有困难，有这些问题并非由缺乏动机、感觉障碍、不适当教学技巧、以及环境所直接造成的。书写时经常犯错，字体结构不对称，书写潦草，漏写笔画，或者同一个字在一篇文章中有的写对，有的写错，会出现加字、漏字、用别的字（词）替代，书写颠倒或是笔画不对——不论是数字或是中英文字，默写常常不及格；书写速度慢，抄写时需要看一笔写一笔，或者只能看一个偏旁或一个字，需要比同龄人用更多时间来完成抄写作业，考试会出现做不完的情况；写作业时常写出界，对不齐作业本的行或格等。作文时常常词不达意，次序混乱，条理不清，文法错误。因为在书写上体验到较多的负面反馈，常常对写字抱有回避心理，不愿意写字。");
        anslist.add("1-3分：注意力水平较好。注意是心理活动对一定对象的指向和集中，是伴随着感知觉、记忆、思维、想象等心理过程的一种共同的心理特征。注意有两个基本特征，一个是指向性，是指心理活动有选择的反映一些现象而离开其余对象。二是集中性，是指心理活动停留在被选择对象上的强度或紧张。指向性表现为对出现在同一时间的许多刺激的选择；集中性表现为对干扰刺激的抑制。注意力有四种特质，即注意的广度、注意的稳定性、注意的分配和注意的转移，这是衡量一个人注意力好坏的标志。");
        anslist.add("4-6分：有中度的注意力不集中。无法长时间将注意力集中于课业或活动上（电子游戏除外），粗心，经常因为不注意细节而犯粗心大意的错误或者遗失物品，较容易被外界刺激吸引而转移注意力，做作业拖拉磨蹭，经常喝水、上厕所、玩东西。孩子在5岁以前有注意力不集中是很常见的，如果能通过科学的锻炼，是可以提升注意力水平的。中度的注意力不集中，可以通过科学的训练来得到改善。");
        anslist.add("7-9分：严重注意力不集中，得分越高，问题越严重。严重的注意力不集中，将会影响到孩子的个性、学习、人际交往以及自信心建立，随着年龄增长，因自控力差易受不良影响和引诱，可发生厌学、打架斗殴、说谎偷窃，甚至走上犯罪的道路。注意力不集中的主要表现有：无法注意细节，学习和活动中常因粗心而犯错；学习和做事很难维持专注的状态，很容易发生随意性转移；无法完成他人的指令，不能完成按时自己的作业或其他任务；生活中丢三落四；跟他说话经常表现出没有在听的样子；对组织性的活动或任务感到困难；逃避或者厌恶需要花费心思的活动及事情；无法正常端坐，身体经常动来动去，玩手或其他小物品；很难安静的玩或学习，活动过多，静不下来；无法抑制冲动，常常问题还没有问完就急着说出答案，在不适当的场合打断别人说话，难以静静的等待排队。作业拖拉磨蹭，常常要比别人多用几倍的时间，考试时常常因为时间不够而不能完成试卷。学习成绩受到影响，成绩波动大，或下滑厉害，但这种表现的成绩差与智力无关；感知觉功能异常，拿筷子、握笔书写、扣纽扣、系鞋带、做手工操作等精细动作笨拙，阅读时眼球运动不协调，认字时把偏旁相近的字搞混淆，如6与9，b与p之间区分困难，有的会存在品行问题，故意跟家长对着干，在校屡屡违法纪律；人际交往能力差，自我为中心，干扰别人，不能体察别人的感受，不遵守规则，游戏时不能依次轮流等待；常常伴随情绪问题，遇到不愉快的事总是发脾气，易激惹，起极端，甚至出现对抗大人，攻击他人的行为。对于严重的注意力不集中，越早干预越好，若不及早关注，可能会影响孩子的一生。");
        anslist.add("1-3分：轻度多动，久坐困难，无意识的动来动去，手脚常动来动去，或者把玩小东西，但不影响当下的学习或活动，需要保持关注，善加引导，可以有效地提高。");
        anslist.add("4-6分：中度多动，手脚长不安的动来动去，久坐不住，或者在不适合的场合跑来跑去，很难安静的玩或从事其他活动，经常处于活动状态，话多，常打断或者干扰别人的活动。课堂上无法把握细节，在作业中常常会以为粗心犯错，做事或者活动很难持续维持专注，很容易被干扰，会一件事未做完又做另一件事。");
        anslist.add("7-9分：重度多动：无法集中注意力，别人说话经常表现出没有在听的样子，很难依照指示完成事情，无法完成作业或家务，对组织性的工作或规划活动感到困难；逃避或厌恶需要花费心思的活动或工作；常常忘东忘西（如书本或工作需要的东西）活要做的事；在需要坐着的时候不自觉的站起来或离开位置，总是静不下来，永远都在做一些事情或者动个不停。极度爱讲话，无法等待轮到他，常常在不合适的状况下打断活动或讲话，不能按照别人的指示去做事，经常丢失在学校或家中学习和活动要用的物品，（如玩具，铅笔，书和作业本），常常参与对身体有危险的活动而不考虑可能导致的后果（不是为了寻求刺激）。有明显广泛的学校、家庭和伙伴关系的社会功能的损害。");
        anslist.add("1-3分：学习和感知能力较好，具备较好的学习态度和较强的学习能力，感统协调，有很好的感知觉能力。会使学习比较轻松和愉快。");
        anslist.add("4-6分：学习和感知能力有中度不足。这种不足更多体现在认识、态度和动机上，对于学习的认识不足，目标不明确，学习的社会意义和个人意义不统一。学习动力不强，学习态度是被动的，对于学习缺乏持久的兴趣和动力，自觉性不强，善加引导，可以培养孩子良好的学习态度和动机水平。");
        anslist.add("7-9分：学习和感知能力差。在听、说、读、写、计算、思考等学习能力的某一方面或某几方面表现出困难。方向感较差，分辨上下左右困难，穿鞋常常会穿反；阅读写作时常常倒转字词，协调能力不强，体育表现差，音律感差，不能依照一些简单的节拍打拍，与同龄的孩子相比，系鞋带扣纽扣显得笨拙，记忆困难，常常遗失或忘记，记不得与自己相关的资料，如电话、地址等。学习成绩差，成绩下滑明显，缺乏好奇心，对学习没有兴趣，或兴趣不能持久。动机水平低，缺乏目标，只停留在口头上，很少落实到实际中。学习态度不良，呈现一种漫无目的的被动学习倾向，缺乏热情和自觉性。自律性差，不能与同学建立良好的人际关系，为寻求心理补偿，会出现逆反心理和情绪对抗。自我评价低，不自信，容易受到挫折，易形成习惯性惰性和自我无助的心理，社会适应技能不足，凡事都要依赖别人。");
        anslist.add("1-3分：情绪控制能力和情绪调节能力尚好，有积极的情绪表达能力和合理的消极情绪疏导能力，能很快平复自己的情绪，懂得体谅他人，能很快的适应陌生的环境，遇到挫折也能不焦不躁的面对，情绪体验比较稳定平和。");
        anslist.add("4-6分：情绪控制能力稍弱，情绪调节能力稍慢，在情绪发作的时候常常不能照顾到别人的感受，遇到挫折的时候常常久久不能释怀，消极情绪的体验感比较强、持续时间会比较长，面对压力的时候容易发脾气。");
        anslist.add("7-9分：情绪的控制能力和调节能力比较差，生气的时候不能控制自己的情绪，并且没有合理的情绪表达方式，容易发生过激行为，比如哭闹不停，摔东西，打人等，适应新环境比较慢，常常要经过很长一段时间才能适应，在新环境中不敢、不主动与人交往，遇到挫折和压力的时候，消极情绪的体验感很强，并且会因此发生情绪爆发、攻击性行为或者回避行为、诉说不舒服。人际关系比较差，常常因为自己的情绪而不能很好的与人相处。");
        anslist.add("");

    }

    @OnClick(R.id.last)
    public void onViewClicked() {
        crrentindex--;
        tvTitle.setText(datalist.get(crrentindex));
        radiogroup.setClickable(true);
        ans3.setClickable(true);
        ans2.setClickable(true);
        ans1.setClickable(true);
        radiogroup.clearCheck();
    }
}
