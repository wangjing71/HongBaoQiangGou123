package wj.bean;

import java.util.List;

public class DataBean {

    private int code;
    private DataDTO data;
    private String msg;
    private String traceId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public static class DataDTO {
        private String canUseCoinAmount;
        private List<CashExchangeRuleListDTO> cashExchangeRuleList;
        private ClientConfDTO clientConf;
        private String exchangeBizCode;
        private List<HbExchangeRuleListDTO> hbExchangeRuleList;
        private int stockPersonDayLimit;
        private int stockPersonDayUsed;
        private String waitCoinAmount;

        public String getCanUseCoinAmount() {
            return canUseCoinAmount;
        }

        public void setCanUseCoinAmount(String canUseCoinAmount) {
            this.canUseCoinAmount = canUseCoinAmount;
        }

        public List<CashExchangeRuleListDTO> getCashExchangeRuleList() {
            return cashExchangeRuleList;
        }

        public void setCashExchangeRuleList(List<CashExchangeRuleListDTO> cashExchangeRuleList) {
            this.cashExchangeRuleList = cashExchangeRuleList;
        }

        public ClientConfDTO getClientConf() {
            return clientConf;
        }

        public void setClientConf(ClientConfDTO clientConf) {
            this.clientConf = clientConf;
        }

        public String getExchangeBizCode() {
            return exchangeBizCode;
        }

        public void setExchangeBizCode(String exchangeBizCode) {
            this.exchangeBizCode = exchangeBizCode;
        }

        public List<HbExchangeRuleListDTO> getHbExchangeRuleList() {
            return hbExchangeRuleList;
        }

        public void setHbExchangeRuleList(List<HbExchangeRuleListDTO> hbExchangeRuleList) {
            this.hbExchangeRuleList = hbExchangeRuleList;
        }

        public int getStockPersonDayLimit() {
            return stockPersonDayLimit;
        }

        public void setStockPersonDayLimit(int stockPersonDayLimit) {
            this.stockPersonDayLimit = stockPersonDayLimit;
        }

        public int getStockPersonDayUsed() {
            return stockPersonDayUsed;
        }

        public void setStockPersonDayUsed(int stockPersonDayUsed) {
            this.stockPersonDayUsed = stockPersonDayUsed;
        }

        public String getWaitCoinAmount() {
            return waitCoinAmount;
        }

        public void setWaitCoinAmount(String waitCoinAmount) {
            this.waitCoinAmount = waitCoinAmount;
        }

        public static class ClientConfDTO {
            private double _utime;
            private NormalConfigDTO normalConfig;
            private PromoteConfigDTO promoteConfig;
            private String recommendId;
            private List<String> rule;
            private List<String> ruleBusiness;
            private TaskConfigDTO taskConfig;
            private String userBIsHideNewerFloor;
            private String userBIsMustToApp;
            private String userBOldGuideMaxAmount;

            public double get_utime() {
                return _utime;
            }

            public void set_utime(double _utime) {
                this._utime = _utime;
            }

            public NormalConfigDTO getNormalConfig() {
                return normalConfig;
            }

            public void setNormalConfig(NormalConfigDTO normalConfig) {
                this.normalConfig = normalConfig;
            }

            public PromoteConfigDTO getPromoteConfig() {
                return promoteConfig;
            }

            public void setPromoteConfig(PromoteConfigDTO promoteConfig) {
                this.promoteConfig = promoteConfig;
            }

            public String getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(String recommendId) {
                this.recommendId = recommendId;
            }

            public List<String> getRule() {
                return rule;
            }

            public void setRule(List<String> rule) {
                this.rule = rule;
            }

            public List<String> getRuleBusiness() {
                return ruleBusiness;
            }

            public void setRuleBusiness(List<String> ruleBusiness) {
                this.ruleBusiness = ruleBusiness;
            }

            public TaskConfigDTO getTaskConfig() {
                return taskConfig;
            }

            public void setTaskConfig(TaskConfigDTO taskConfig) {
                this.taskConfig = taskConfig;
            }

            public String getUserBIsHideNewerFloor() {
                return userBIsHideNewerFloor;
            }

            public void setUserBIsHideNewerFloor(String userBIsHideNewerFloor) {
                this.userBIsHideNewerFloor = userBIsHideNewerFloor;
            }

            public String getUserBIsMustToApp() {
                return userBIsMustToApp;
            }

            public void setUserBIsMustToApp(String userBIsMustToApp) {
                this.userBIsMustToApp = userBIsMustToApp;
            }

            public String getUserBOldGuideMaxAmount() {
                return userBOldGuideMaxAmount;
            }

            public void setUserBOldGuideMaxAmount(String userBOldGuideMaxAmount) {
                this.userBOldGuideMaxAmount = userBOldGuideMaxAmount;
            }

            public static class NormalConfigDTO {
                private String assetsComingTips;
                private String exchangePersonDayLimitTips;
                private String guestHomeTitle;
                private String guestRecommendTitle;
                private String guideTips;
                private String hbSubtitle;
                private String homeExchangeTitle;
                private int homeGuideOrderTimes;
                private String homeIsSimpleGuideStep;
                private String homeOrderIcon;
                private String homeRecommendTitle;
                private String homeSignIcon;
                private String homeSweepIcon;
                private String homeTaskSubtitle;
                private String homeTaskTitle;
                private String mainBtnDesc;
                private String newerShareTips;
                private String newerShareTitle;
                private String oldGuideBtnButton;
                private String oldGuideMaxAmount;
                private String orderInfoTips;
                private String orderMaxLimitTips;
                private String shopAddBtnText;
                private String shopModifyTips;
                private String shopNormalTips;
                private String shopShareBtnText;
                private String shopTopBg;
                private String signInfoTips;
                private String skinBg;
                private String skinBgGuest;
                private String skinTitle;
                private String squareAddBtnText;
                private String squareDownBtnText;
                private String squareTitleImage;
                private String squareTopBg;
                private String subTitleGuest;
                private String toWxShowDesc;
                private String toWxShowTips;

                public String getAssetsComingTips() {
                    return assetsComingTips;
                }

                public void setAssetsComingTips(String assetsComingTips) {
                    this.assetsComingTips = assetsComingTips;
                }

                public String getExchangePersonDayLimitTips() {
                    return exchangePersonDayLimitTips;
                }

                public void setExchangePersonDayLimitTips(String exchangePersonDayLimitTips) {
                    this.exchangePersonDayLimitTips = exchangePersonDayLimitTips;
                }

                public String getGuestHomeTitle() {
                    return guestHomeTitle;
                }

                public void setGuestHomeTitle(String guestHomeTitle) {
                    this.guestHomeTitle = guestHomeTitle;
                }

                public String getGuestRecommendTitle() {
                    return guestRecommendTitle;
                }

                public void setGuestRecommendTitle(String guestRecommendTitle) {
                    this.guestRecommendTitle = guestRecommendTitle;
                }

                public String getGuideTips() {
                    return guideTips;
                }

                public void setGuideTips(String guideTips) {
                    this.guideTips = guideTips;
                }

                public String getHbSubtitle() {
                    return hbSubtitle;
                }

                public void setHbSubtitle(String hbSubtitle) {
                    this.hbSubtitle = hbSubtitle;
                }

                public String getHomeExchangeTitle() {
                    return homeExchangeTitle;
                }

                public void setHomeExchangeTitle(String homeExchangeTitle) {
                    this.homeExchangeTitle = homeExchangeTitle;
                }

                public int getHomeGuideOrderTimes() {
                    return homeGuideOrderTimes;
                }

                public void setHomeGuideOrderTimes(int homeGuideOrderTimes) {
                    this.homeGuideOrderTimes = homeGuideOrderTimes;
                }

                public String getHomeIsSimpleGuideStep() {
                    return homeIsSimpleGuideStep;
                }

                public void setHomeIsSimpleGuideStep(String homeIsSimpleGuideStep) {
                    this.homeIsSimpleGuideStep = homeIsSimpleGuideStep;
                }

                public String getHomeOrderIcon() {
                    return homeOrderIcon;
                }

                public void setHomeOrderIcon(String homeOrderIcon) {
                    this.homeOrderIcon = homeOrderIcon;
                }

                public String getHomeRecommendTitle() {
                    return homeRecommendTitle;
                }

                public void setHomeRecommendTitle(String homeRecommendTitle) {
                    this.homeRecommendTitle = homeRecommendTitle;
                }

                public String getHomeSignIcon() {
                    return homeSignIcon;
                }

                public void setHomeSignIcon(String homeSignIcon) {
                    this.homeSignIcon = homeSignIcon;
                }

                public String getHomeSweepIcon() {
                    return homeSweepIcon;
                }

                public void setHomeSweepIcon(String homeSweepIcon) {
                    this.homeSweepIcon = homeSweepIcon;
                }

                public String getHomeTaskSubtitle() {
                    return homeTaskSubtitle;
                }

                public void setHomeTaskSubtitle(String homeTaskSubtitle) {
                    this.homeTaskSubtitle = homeTaskSubtitle;
                }

                public String getHomeTaskTitle() {
                    return homeTaskTitle;
                }

                public void setHomeTaskTitle(String homeTaskTitle) {
                    this.homeTaskTitle = homeTaskTitle;
                }

                public String getMainBtnDesc() {
                    return mainBtnDesc;
                }

                public void setMainBtnDesc(String mainBtnDesc) {
                    this.mainBtnDesc = mainBtnDesc;
                }

                public String getNewerShareTips() {
                    return newerShareTips;
                }

                public void setNewerShareTips(String newerShareTips) {
                    this.newerShareTips = newerShareTips;
                }

                public String getNewerShareTitle() {
                    return newerShareTitle;
                }

                public void setNewerShareTitle(String newerShareTitle) {
                    this.newerShareTitle = newerShareTitle;
                }

                public String getOldGuideBtnButton() {
                    return oldGuideBtnButton;
                }

                public void setOldGuideBtnButton(String oldGuideBtnButton) {
                    this.oldGuideBtnButton = oldGuideBtnButton;
                }

                public String getOldGuideMaxAmount() {
                    return oldGuideMaxAmount;
                }

                public void setOldGuideMaxAmount(String oldGuideMaxAmount) {
                    this.oldGuideMaxAmount = oldGuideMaxAmount;
                }

                public String getOrderInfoTips() {
                    return orderInfoTips;
                }

                public void setOrderInfoTips(String orderInfoTips) {
                    this.orderInfoTips = orderInfoTips;
                }

                public String getOrderMaxLimitTips() {
                    return orderMaxLimitTips;
                }

                public void setOrderMaxLimitTips(String orderMaxLimitTips) {
                    this.orderMaxLimitTips = orderMaxLimitTips;
                }

                public String getShopAddBtnText() {
                    return shopAddBtnText;
                }

                public void setShopAddBtnText(String shopAddBtnText) {
                    this.shopAddBtnText = shopAddBtnText;
                }

                public String getShopModifyTips() {
                    return shopModifyTips;
                }

                public void setShopModifyTips(String shopModifyTips) {
                    this.shopModifyTips = shopModifyTips;
                }

                public String getShopNormalTips() {
                    return shopNormalTips;
                }

                public void setShopNormalTips(String shopNormalTips) {
                    this.shopNormalTips = shopNormalTips;
                }

                public String getShopShareBtnText() {
                    return shopShareBtnText;
                }

                public void setShopShareBtnText(String shopShareBtnText) {
                    this.shopShareBtnText = shopShareBtnText;
                }

                public String getShopTopBg() {
                    return shopTopBg;
                }

                public void setShopTopBg(String shopTopBg) {
                    this.shopTopBg = shopTopBg;
                }

                public String getSignInfoTips() {
                    return signInfoTips;
                }

                public void setSignInfoTips(String signInfoTips) {
                    this.signInfoTips = signInfoTips;
                }

                public String getSkinBg() {
                    return skinBg;
                }

                public void setSkinBg(String skinBg) {
                    this.skinBg = skinBg;
                }

                public String getSkinBgGuest() {
                    return skinBgGuest;
                }

                public void setSkinBgGuest(String skinBgGuest) {
                    this.skinBgGuest = skinBgGuest;
                }

                public String getSkinTitle() {
                    return skinTitle;
                }

                public void setSkinTitle(String skinTitle) {
                    this.skinTitle = skinTitle;
                }

                public String getSquareAddBtnText() {
                    return squareAddBtnText;
                }

                public void setSquareAddBtnText(String squareAddBtnText) {
                    this.squareAddBtnText = squareAddBtnText;
                }

                public String getSquareDownBtnText() {
                    return squareDownBtnText;
                }

                public void setSquareDownBtnText(String squareDownBtnText) {
                    this.squareDownBtnText = squareDownBtnText;
                }

                public String getSquareTitleImage() {
                    return squareTitleImage;
                }

                public void setSquareTitleImage(String squareTitleImage) {
                    this.squareTitleImage = squareTitleImage;
                }

                public String getSquareTopBg() {
                    return squareTopBg;
                }

                public void setSquareTopBg(String squareTopBg) {
                    this.squareTopBg = squareTopBg;
                }

                public String getSubTitleGuest() {
                    return subTitleGuest;
                }

                public void setSubTitleGuest(String subTitleGuest) {
                    this.subTitleGuest = subTitleGuest;
                }

                public String getToWxShowDesc() {
                    return toWxShowDesc;
                }

                public void setToWxShowDesc(String toWxShowDesc) {
                    this.toWxShowDesc = toWxShowDesc;
                }

                public String getToWxShowTips() {
                    return toWxShowTips;
                }

                public void setToWxShowTips(String toWxShowTips) {
                    this.toWxShowTips = toWxShowTips;
                }
            }

            public static class PromoteConfigDTO {
                private int endTime;
                private String skinBg;
                private String skinBgGuest;
                private String skinTitle;
                private int startTime;

                public int getEndTime() {
                    return endTime;
                }

                public void setEndTime(int endTime) {
                    this.endTime = endTime;
                }

                public String getSkinBg() {
                    return skinBg;
                }

                public void setSkinBg(String skinBg) {
                    this.skinBg = skinBg;
                }

                public String getSkinBgGuest() {
                    return skinBgGuest;
                }

                public void setSkinBgGuest(String skinBgGuest) {
                    this.skinBgGuest = skinBgGuest;
                }

                public String getSkinTitle() {
                    return skinTitle;
                }

                public void setSkinTitle(String skinTitle) {
                    this.skinTitle = skinTitle;
                }

                public int getStartTime() {
                    return startTime;
                }

                public void setStartTime(int startTime) {
                    this.startTime = startTime;
                }
            }

            public static class TaskConfigDTO {
                private double _utime;
                private List<OrderJCommandDTO> orderJCommand;
                private String shareChannel;
                private String shareOrderGuide;
                private List<ShareOrderInfoDTO> shareOrderInfo;
                private String shareSignGuide;
                private List<ShareSignInfoDTO> shareSignInfo;
                private List<SignJCommandDTO> signJCommand;
                private String signShareChannel;

                public double get_utime() {
                    return _utime;
                }

                public void set_utime(double _utime) {
                    this._utime = _utime;
                }

                public List<OrderJCommandDTO> getOrderJCommand() {
                    return orderJCommand;
                }

                public void setOrderJCommand(List<OrderJCommandDTO> orderJCommand) {
                    this.orderJCommand = orderJCommand;
                }

                public String getShareChannel() {
                    return shareChannel;
                }

                public void setShareChannel(String shareChannel) {
                    this.shareChannel = shareChannel;
                }

                public String getShareOrderGuide() {
                    return shareOrderGuide;
                }

                public void setShareOrderGuide(String shareOrderGuide) {
                    this.shareOrderGuide = shareOrderGuide;
                }

                public List<ShareOrderInfoDTO> getShareOrderInfo() {
                    return shareOrderInfo;
                }

                public void setShareOrderInfo(List<ShareOrderInfoDTO> shareOrderInfo) {
                    this.shareOrderInfo = shareOrderInfo;
                }

                public String getShareSignGuide() {
                    return shareSignGuide;
                }

                public void setShareSignGuide(String shareSignGuide) {
                    this.shareSignGuide = shareSignGuide;
                }

                public List<ShareSignInfoDTO> getShareSignInfo() {
                    return shareSignInfo;
                }

                public void setShareSignInfo(List<ShareSignInfoDTO> shareSignInfo) {
                    this.shareSignInfo = shareSignInfo;
                }

                public List<SignJCommandDTO> getSignJCommand() {
                    return signJCommand;
                }

                public void setSignJCommand(List<SignJCommandDTO> signJCommand) {
                    this.signJCommand = signJCommand;
                }

                public String getSignShareChannel() {
                    return signShareChannel;
                }

                public void setSignShareChannel(String signShareChannel) {
                    this.signShareChannel = signShareChannel;
                }

                public static class OrderJCommandDTO {
                    private String _id;
                    private boolean _isPublish;
                    private String content;
                    private String iconUrl;
                    private String title;

                    public String get_id() {
                        return _id;
                    }

                    public void set_id(String _id) {
                        this._id = _id;
                    }

                    public boolean is_isPublish() {
                        return _isPublish;
                    }

                    public void set_isPublish(boolean _isPublish) {
                        this._isPublish = _isPublish;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getIconUrl() {
                        return iconUrl;
                    }

                    public void setIconUrl(String iconUrl) {
                        this.iconUrl = iconUrl;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }

                public static class ShareOrderInfoDTO {
                    private String _id;
                    private boolean _isPublish;
                    private String content;
                    private String iconUrl;
                    private String title;

                    public String get_id() {
                        return _id;
                    }

                    public void set_id(String _id) {
                        this._id = _id;
                    }

                    public boolean is_isPublish() {
                        return _isPublish;
                    }

                    public void set_isPublish(boolean _isPublish) {
                        this._isPublish = _isPublish;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getIconUrl() {
                        return iconUrl;
                    }

                    public void setIconUrl(String iconUrl) {
                        this.iconUrl = iconUrl;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }

                public static class ShareSignInfoDTO {
                    private String _id;
                    private boolean _isPublish;
                    private String content;
                    private String iconUrl;
                    private String title;

                    public String get_id() {
                        return _id;
                    }

                    public void set_id(String _id) {
                        this._id = _id;
                    }

                    public boolean is_isPublish() {
                        return _isPublish;
                    }

                    public void set_isPublish(boolean _isPublish) {
                        this._isPublish = _isPublish;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getIconUrl() {
                        return iconUrl;
                    }

                    public void setIconUrl(String iconUrl) {
                        this.iconUrl = iconUrl;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }

                public static class SignJCommandDTO {
                    private String _id;
                    private boolean _isPublish;
                    private String content;
                    private String iconUrl;
                    private String title;

                    public String get_id() {
                        return _id;
                    }

                    public void set_id(String _id) {
                        this._id = _id;
                    }

                    public boolean is_isPublish() {
                        return _isPublish;
                    }

                    public void set_isPublish(boolean _isPublish) {
                        this._isPublish = _isPublish;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getIconUrl() {
                        return iconUrl;
                    }

                    public void setIconUrl(String iconUrl) {
                        this.iconUrl = iconUrl;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }

        public static class CashExchangeRuleListDTO {
            private String attribute;
            private String cashoutAmount;
            private String consumeScore;
            private String description;
            private int exchangeStatus;
            private int exchangeType;
            private String id;
            private List<String> images;
            private String name;
            private String orderAmount;
            private String orderSkuId;

            public String getAttribute() {
                return attribute;
            }

            public void setAttribute(String attribute) {
                this.attribute = attribute;
            }

            public String getCashoutAmount() {
                return cashoutAmount;
            }

            public void setCashoutAmount(String cashoutAmount) {
                this.cashoutAmount = cashoutAmount;
            }

            public String getConsumeScore() {
                return consumeScore;
            }

            public void setConsumeScore(String consumeScore) {
                this.consumeScore = consumeScore;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getExchangeStatus() {
                return exchangeStatus;
            }

            public void setExchangeStatus(int exchangeStatus) {
                this.exchangeStatus = exchangeStatus;
            }

            public int getExchangeType() {
                return exchangeType;
            }

            public void setExchangeType(int exchangeType) {
                this.exchangeType = exchangeType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(String orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderSkuId() {
                return orderSkuId;
            }

            public void setOrderSkuId(String orderSkuId) {
                this.orderSkuId = orderSkuId;
            }
        }

        public static class HbExchangeRuleListDTO {
            private String attribute;
            private String cashoutAmount;
            private String consumeScore;
            private String description;
            private int exchangeStatus;
            private int exchangeType;
            private ExtMapDTO extMap;
            private String id;
            private List<String> images;
            private String name;
            private String orderAmount;
            private String orderSkuId;

            public String getAttribute() {
                return attribute;
            }

            public void setAttribute(String attribute) {
                this.attribute = attribute;
            }

            public String getCashoutAmount() {
                return cashoutAmount;
            }

            public void setCashoutAmount(String cashoutAmount) {
                this.cashoutAmount = cashoutAmount;
            }

            public String getConsumeScore() {
                return consumeScore;
            }

            public void setConsumeScore(String consumeScore) {
                this.consumeScore = consumeScore;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getExchangeStatus() {
                return exchangeStatus;
            }

            public void setExchangeStatus(int exchangeStatus) {
                this.exchangeStatus = exchangeStatus;
            }

            public int getExchangeType() {
                return exchangeType;
            }

            public void setExchangeType(int exchangeType) {
                this.exchangeType = exchangeType;
            }

            public ExtMapDTO getExtMap() {
                return extMap;
            }

            public void setExtMap(ExtMapDTO extMap) {
                this.extMap = extMap;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(String orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderSkuId() {
                return orderSkuId;
            }

            public void setOrderSkuId(String orderSkuId) {
                this.orderSkuId = orderSkuId;
            }

            public static class ExtMapDTO {
                private String active;
                private String couponDiscount;
                private String couponQuota;
                private int prizeLevel;
                private int prizeType;

                public String getActive() {
                    return active;
                }

                public void setActive(String active) {
                    this.active = active;
                }

                public String getCouponDiscount() {
                    return couponDiscount;
                }

                public void setCouponDiscount(String couponDiscount) {
                    this.couponDiscount = couponDiscount;
                }

                public String getCouponQuota() {
                    return couponQuota;
                }

                public void setCouponQuota(String couponQuota) {
                    this.couponQuota = couponQuota;
                }

                public int getPrizeLevel() {
                    return prizeLevel;
                }

                public void setPrizeLevel(int prizeLevel) {
                    this.prizeLevel = prizeLevel;
                }

                public int getPrizeType() {
                    return prizeType;
                }

                public void setPrizeType(int prizeType) {
                    this.prizeType = prizeType;
                }
            }
        }
    }
}
